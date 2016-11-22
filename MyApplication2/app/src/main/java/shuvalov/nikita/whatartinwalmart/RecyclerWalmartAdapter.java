package shuvalov.nikita.whatartinwalmart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by NikitaShuvalov on 11/17/16.
 */

public class RecyclerWalmartAdapter extends RecyclerView.Adapter<WalmartViewHolder> {
    private ArrayList<WalmartItem> mWalmartItems;

    public RecyclerWalmartAdapter(ArrayList<WalmartItem> walmartItems) {
        mWalmartItems = walmartItems;
    }

    @Override
    public WalmartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WalmartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_form, null));
    }

    @Override
    public void onBindViewHolder(WalmartViewHolder holder, int position) {
        holder.mTextView.setText(mWalmartItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mWalmartItems.size();
    }
}

class WalmartViewHolder extends RecyclerView.ViewHolder{
    TextView mTextView;

    public WalmartViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView)itemView.findViewById(R.id.item_display);
    }
}
