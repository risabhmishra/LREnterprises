package risabhmishra.com.lrenterprises.Model;

public class Company {
    String CODE,PRODUCT,RATE,TYPE;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    Boolean status = false;

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getPRODUCT() {
        return PRODUCT;
    }

    public void setPRODUCT(String PRODUCT) {
        this.PRODUCT = PRODUCT;
    }

    public String getRATE() {
        return String.format("%.2f",Float.parseFloat(RATE)/1.18);
    }

    public void setRATE(String RATE) {
        this.RATE = RATE;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public Company(String CODE, String PRODUCT, String RATE, String TYPE) {
        this.CODE = CODE;
        this.PRODUCT = PRODUCT;
        this.RATE = RATE;
        this.TYPE = TYPE;
        this.status = false;
    }

    public Company() {
    }
}
