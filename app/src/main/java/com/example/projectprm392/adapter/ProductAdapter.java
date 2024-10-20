package com.example.projectprm392.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectprm392.ProductDetailMainActivity;
import com.example.projectprm392.R;
import com.example.projectprm392.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import kotlin.coroutines.EmptyCoroutineContext;

public class ProductAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> listProduct;

    public ProductAdapter(Context context, List<Product> listProduct) {
        this.mContext = context;
        this.listProduct = listProduct;
    }

    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return listProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductViewHolder holder;
        if(convertView == null) {  //neu chua co view thi tao view moi
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_view_product2, parent, false);
            holder = new ProductViewHolder();
            holder.holder_product_img = convertView.findViewById(R.id.item_product_image);
            holder.holder_product_name = convertView.findViewById(R.id.item_product_name);
            holder.holder_product_price = convertView.findViewById(R.id.item_product_price);
            convertView.setTag(holder);
        }
        //da co view
        else {
            holder = (ProductViewHolder) convertView.getTag();
        }
        Product p = listProduct.get(position);
        if(p != null) {
            Picasso.get().load(p.getImage()).into(holder.holder_product_img);
            holder.holder_product_name.setText(p.getProductName());
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedPrice = formatter.format(Double.parseDouble(p.getPrice()));
            holder.holder_product_price.setText(formattedPrice + " VND");
        }

        //product detail
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product p = listProduct.get(position);
                Intent intent = new Intent(mContext, ProductDetailMainActivity.class);
                intent.putExtra("PRD", p);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ProductViewHolder {
        ImageView holder_product_img;
        TextView holder_product_name, holder_product_price;
    }
}
