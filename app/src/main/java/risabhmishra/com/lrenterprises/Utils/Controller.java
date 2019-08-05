package risabhmishra.com.lrenterprises.Utils;
import android.app.Application;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import risabhmishra.com.lrenterprises.Model.CartItem;


import static java.lang.Integer.parseInt;
import static risabhmishra.com.lrenterprises.UI.BrandSelect.tv_cart_badge;
import static risabhmishra.com.lrenterprises.UI.CartActivity.tvTotalCart;

public class Controller extends Application {

    private static ArrayList <CartItem> vegetableItems = new ArrayList<>();
    private static ArrayList<CartItem> cartItems = new ArrayList<>();
    private static float cartTotal = 0;
    private static int cartNumOfItems = 0;

    public static CartItem getCartItemByPosition(int position) {
        return vegetableItems.get(position);
    }

    public static CartItem getVegetableItemByName(String name) {
        for(CartItem item:vegetableItems) {
            if(name.toLowerCase().contains(item.getName().toLowerCase())) {
                return item;
            }
        }
        return null;
    }
    public static Integer getVegetableQuantityFromName(String name) {

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(name);
        if(m.find()) {
            return parseInt(m.group());
        }
        return 0;
    }

    public static void emptyCart() {
        for(CartItem item:vegetableItems) {
            item.setQty("0");
        }
        cartItems.clear();
        cartNumOfItems = 0;
        cartTotal = 0;
        updateCartTotal();
    }

    public static void setVegetableItem(CartItem item) {
        vegetableItems.add(item);
    }

    public static ArrayList<CartItem> getVegetableItems() {
        return vegetableItems;
    }

    public static int getQuantityOfItemInCart(CartItem item) {
        return 0;
    }

    public static boolean checkItemInCart(CartItem item) {
        return cartItems.contains(item);
    }

    public static void addItemToCart(CartItem item) {
        cartItems.add(item);
        cartNumOfItems += 1;
        updateCartCount();
        addToCartTotal(Float.parseFloat(item.getAmount()));
    }

    public static void removeItemFromCart(CartItem item) {
        if(checkItemInCart(item)) {
            cartNumOfItems -= 1;
                cartItems.remove(item);
                removeFromCartTotal(Float.parseFloat(item.getAmount()));
            updateCartTotal();
            updateCartCount();
        }
    }

    public static ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public static void setCartItems(String cartitems) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        cartItems = gson.fromJson(cartitems,type);

        if(cartItems == null)
            cartItems = new ArrayList<>();
    }

    public static float getCartTotal() {
        return cartTotal;
    }

    public static void addToCartTotal(float amount) {
        cartTotal = cartTotal + amount;
        updateCartTotal();
    }

    public static float removeFromCartTotal(float amount) {
        cartTotal = cartTotal - amount;
        updateCartTotal();
        return cartTotal;
    }

    public static void modifyCart(CartItem item) {
        int id = cartItems.indexOf(item);

        if(id == -1) {
            addItemToCart(item);
        } else {
            cartItems.get(id).setQty(item.getQty());
        }
    }

    public static float modifyCartTotal(float amount) {
        cartTotal = amount;
        updateCartTotal();
        return cartTotal;
    }

    public static void updateCartCount()
    {
        if(cartNumOfItems > 0) {
            tv_cart_badge.setVisibility(View.VISIBLE);
        } else {
            tv_cart_badge.setVisibility(View.INVISIBLE);
        }

        tv_cart_badge.setText(String.valueOf(cartNumOfItems));
    }


    public static void updateCartTotal() {

        if(tvTotalCart != null) {

            tvTotalCart.setText(String.format("%.2f",cartTotal));

        }
    }

}
