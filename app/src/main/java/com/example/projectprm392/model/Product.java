package com.example.projectprm392.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {
    private String productId;
    private String brandId;
    private String brandName;
    private String productName;
    private int quantity;
    private String description;
    private String price;
    private String image;
    private int status;

    public Product() {
    }

    public Product(String productId, String brandId, String brandName, String productName, int quantity
            , String description, String price, String image, int status) {
        this.productId = productId;
        this.brandId = brandId;
        this.brandName = brandName;
        this.productName = productName;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.image = image;
        this.status = status;
    }

    protected Product(Parcel in) {
        productId = in.readString();
        brandId = in.readString();
        brandName = in.readString();
        productName = in.readString();
        quantity = in.readInt();
        description = in.readString();
        price = in.readString();
        image = in.readString();
        status = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(brandId);
        dest.writeString(brandName);
        dest.writeString(productName);
        dest.writeInt(quantity);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(image);
        dest.writeInt(status);
    }
}
