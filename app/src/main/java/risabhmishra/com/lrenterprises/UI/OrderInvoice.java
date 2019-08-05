package risabhmishra.com.lrenterprises.UI;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import risabhmishra.com.lrenterprises.Fragments.UpcomingFragment;
import risabhmishra.com.lrenterprises.MainActivity;
import risabhmishra.com.lrenterprises.Model.CartItem;
import risabhmishra.com.lrenterprises.R;
import risabhmishra.com.lrenterprises.Utils.Controller;
import risabhmishra.com.lrenterprises.Utils.EnglishNumberToWords;
import risabhmishra.com.lrenterprises.Utils.Preferences;

public class OrderInvoice extends AppCompatActivity {
    private ImageView ivDownload;
    private TextView tvOrderNumber, tvTotalAmount, tvDate;
    private LinearLayout llScroll;
    private Bitmap bitmap;
    private LinearLayout invoiceRecyclerView;
    private TextView tvuserName,tvuserGst,tvuserAddress,tvsgst,tvcgst,tvtotalfinal,tvriw;
    private StorageReference storageReference;
    DatabaseReference db;
    String orderNumber;
    File file;
    Uri uri;
    String strDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_invoice);

        FirebaseApp.initializeApp(OrderInvoice.this);
        db = FirebaseDatabase.getInstance().getReference().child("OrderNo");

        ivDownload = (ImageView) findViewById(R.id.iv_download);
        tvOrderNumber = (TextView) findViewById(R.id.tv_invoice_ordernumber);
        tvDate = (TextView) findViewById(R.id.date);
        tvuserName = (TextView) findViewById(R.id.invoice_user_name);
        tvuserGst = (TextView) findViewById(R.id.invoice_user_gst);
        tvuserAddress = (TextView) findViewById(R.id.invoice_user_address);
        tvTotalAmount = (TextView) findViewById(R.id.invoice_total_amount);
        tvsgst = (TextView) findViewById(R.id.invoice_sgst);
        tvcgst = (TextView) findViewById(R.id.invoice_cgst);
        tvtotalfinal = (TextView) findViewById(R.id.invoice_total_plus_tax);
        tvriw = (TextView) findViewById(R.id.rupee_spell);

        llScroll = findViewById(R.id.llscroll);
        invoiceRecyclerView = (LinearLayout) findViewById(R.id.invoice_recyclerview);

storageReference = FirebaseStorage.getInstance().getReference();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderNumber = dataSnapshot.getValue(String.class);
                tvOrderNumber.setText("#" + orderNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        new RequestInvoice().execute();

        tvuserName.setText(Preferences.getUserName(this));
        tvuserGst.setText(Preferences.getUserGst(this));
        tvuserAddress.setText(Preferences.getUserAddress(this));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy");
        strDate = mdformat.format(calendar.getTime());

        tvDate.setText(strDate);

        ivDownload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
//                Log.d("size"," "+llScroll.getWidth() +"  "+llScroll.getWidth());
                openGeneratedPDF();// You can open pdf after complete

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        startActivity(new Intent(OrderInvoice.this, MainActivity.class));
        return true;
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf(){
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);


        Bitmap bitmaps = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmaps, 0, 0, null);
        document.finishPage(page);
        File filePath = new File(Environment.getExternalStorageDirectory().getPath() +"/OrderInvoice.pdf");
        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        file = new File( Environment.getExternalStorageDirectory().getPath() +"/OrderInvoice.pdf");

        final DatabaseReference upcomingRef = FirebaseDatabase.getInstance().getReference().child("UpcomingOrders");
        final StorageReference uploadRef = storageReference.child("OrderInvoice/"+orderNumber);
Uri uri = FileProvider.getUriForFile(this,this.getApplicationContext().getPackageName()+".provider",file);

        uploadRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploadRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        String fileUrl = downloadUrl.toString();
                        HashMap<String,String> hs = new HashMap<>();
                        hs.put("OrderNo",orderNumber);
                        hs.put("Name",Preferences.getUserName(OrderInvoice.this));
                        hs.put("GST",Preferences.getUserGst(OrderInvoice.this));
                        hs.put("PDFUrl",fileUrl);
                        hs.put("Amount",tvtotalfinal.getText().toString());
                        hs.put("Date",strDate);
                        upcomingRef.push().setValue(hs);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    private void openGeneratedPDF(){
        file = new File( Environment.getExternalStorageDirectory().getPath() +"/OrderInvoice.pdf");
        if (file.exists())
        {
            /*
            Intent target=new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(FileProvider.getUriForFile(this,this.getApplicationContext().getPackageName()+".provider",file), "application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Intent intent = Intent.createChooser(target, "Open File");

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(OrderInvoice.this, "Error!", Toast.LENGTH_LONG).show();
            }
*/
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, "manojlily2014@gmail.com");
            email.putExtra(Intent.EXTRA_SUBJECT, "OrderInvoice");
            email.putExtra(Intent.EXTRA_TEXT, Preferences.getUserName(OrderInvoice.this)+"\n"+Preferences.getUserGst(OrderInvoice.this));
            uri = FileProvider.getUriForFile(this,this.getApplicationContext().getPackageName()+".provider",file);//Uri.fromFile(file);
            email.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            email.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            email.putExtra(Intent.EXTRA_STREAM, uri);
            email.setType("application/pdf");
            email.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(email);

        }
    }

    private class RequestInvoice extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

List<CartItem> invoiceItems = new ArrayList<>();
        invoiceItems = Controller.getCartItems();
                        for(int i=0;i<invoiceItems.size();i++)
                        {
                            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.invoice_item,null);
                            CartItem item = invoiceItems.get(i);

                            TextView tvItemNumber = (TextView) view.findViewById(R.id.invoice_item_number);
                            TextView tvItemName = (TextView) view.findViewById(R.id.invoice_item_name);
                            TextView tvItemQuantity = (TextView) view.findViewById(R.id.invoice_item_qty);
                            TextView tvItemPrice = (TextView) view.findViewById(R.id.invoice_item_price);
                            TextView tvItemTotal = (TextView) view.findViewById(R.id.invoice_item_total);
                            TextView tvItemHsn = (TextView) view.findViewById(R.id.invoice_item_hsn);


                            tvItemName.setText(item.getName());
                            tvItemNumber.setText(Integer.toString(i+1) );
                            tvItemQuantity.setText(item.getQty());
                            tvItemPrice.setText(item.getPrice());
                            tvItemTotal.setText(item.getAmount());
                            tvItemHsn.setText("");

                            invoiceRecyclerView.addView(view);
                        }

                        Float total = Controller.getCartTotal();
                        Double tax = 0.09*total;
                        String taxs = String.format("%.2f",tax);
                        Double tf = 2*tax + total;

                        Long lf = Math.round(tf);

                        tvTotalAmount.setText("Rs." + String.format("%.2f",total));
                        tvsgst.setText("Rs." + taxs);
                        tvcgst.setText("Rs." + taxs);
                        tvtotalfinal.setText("Rs." + lf);


                        tvriw.setText("Rupees "+EnglishNumberToWords.convert(lf)+" only.");


            return true;

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bitmap = loadBitmapFromView(llScroll, llScroll.getWidth(), llScroll.getHeight());
                    createPdf();
                    Controller.emptyCart();
                    db.setValue(Integer.toString(Integer.parseInt(orderNumber)+1));

                }
            }, 1000);
        //    bitmap = loadBitmapFromView(llScroll, llScroll.getWidth(), llScroll.getHeight());
           // createPdf();
           // Controller.emptyCart();
        }
    }
}
