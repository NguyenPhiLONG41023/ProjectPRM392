package com.example.projectprm392.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projectprm392.R;
import com.example.projectprm392.model.OrderResponse;
import com.example.projectprm392.model.Product;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderResponse> listOrder;

    public OrderAdapter(Context mContext, List<OrderResponse> listOrder) {
        this.mContext = mContext;
        this.listOrder = listOrder;
    }

    @Override
    public int getCount() {
        return listOrder.size();
    }

    @Override
    public Object getItem(int position) {
        return listOrder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderOrder holderOrder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_view_order, parent, false);
            holderOrder = new ViewHolderOrder();
            holderOrder.orderDate = convertView.findViewById(R.id.tvOrderDate);
            holderOrder.totalPrice = convertView.findViewById(R.id.tvTotalPrice);
            holderOrder.status = convertView.findViewById(R.id.tvStatus);
            convertView.setTag(holderOrder);
        }
        else {
            holderOrder = (ViewHolderOrder) convertView.getTag();
        }
        OrderResponse orderResponse = listOrder.get(position);
        if (orderResponse != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            holderOrder.orderDate.setText(dateFormat.format(orderResponse.getOrderDate()));
            holderOrder.totalPrice.setText(String.format(Locale.getDefault(), "$%.2f", orderResponse.getTotalPrice()));
            holderOrder.status.setText(orderResponse.getStatus());
        }
        return convertView;
    }

    static class ViewHolderOrder{
        TextView orderDate, totalPrice, status;
    }
}
