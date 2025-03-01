package ir.greencode.advicelawAndroid.utils;

import android.content.Context;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ir.greencode.advicelawAndroid.R;


/**
 * Created by Adil on 08/01/2016.
 */
public class GenderAdapter extends RecyclerView.Adapter<GenderAdapter.DialogViewHolder> {

    Context mContext;
    LayoutInflater inflater;
    ArrayList<GenderModel> dataList = new ArrayList<>();
    ItemClickCallBack itemClickCallBack;

    public GenderAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        initList();
    }

    public void setOnItemClickCallBack(ItemClickCallBack onItemClickCallBack) {
        this.itemClickCallBack = onItemClickCallBack;
    }

    @Override
    public DialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DialogViewHolder(inflater.inflate(R.layout.picker_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DialogViewHolder holder, int position) {
        holder.number.setText(dataList.get(position).getGender());
        if (dataList.get(position).isHasFocus()) {
            holder.number.setBackgroundResource(R.drawable.ic_round_shape_selected);
            holder.number.setTextColor(ContextCompat.getColor(mContext,R.color.pickerItemTextColorSelected));
        } else {
            holder.number.setBackgroundResource(R.drawable.ic_round_shape_unselected);
            holder.number.setTextColor(ContextCompat.getColor(mContext,R.color.pickerItemTextColorUnSelected));
        }
    }

    private void initList() {

        dataList.add(new GenderModel(mContext.getString(R.string.MPD_male), true));
        dataList.add(new GenderModel(mContext.getString(R.string.MPD_female), false));

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    int focusedItem = 0;

    class DialogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView number;
        FrameLayout itemParent;


        public DialogViewHolder(View itemView) {
            super(itemView);
            number = (TextView) itemView.findViewById(R.id.text_number);
            itemParent = (FrameLayout) itemView.findViewById(R.id.item_parent);
            itemParent.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            Vibrator vb = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
            vb.vibrate(30);
            if (v.getId() == R.id.item_parent) {
                if (focusedItem <= -1) {
                    focusedItem = getLayoutPosition();
                    dataList.get(getLayoutPosition()).setHasFocus(true);
                    notifyItemChanged(getLayoutPosition());
                } else {
                    dataList.get(focusedItem).setHasFocus(false);
                    notifyItemChanged(focusedItem);
                    focusedItem = getLayoutPosition();
                    dataList.get(getLayoutPosition()).setHasFocus(true);
                    notifyItemChanged(getLayoutPosition());
                }
                itemClickCallBack.onItemClicked(dataList.get(getLayoutPosition()).getGender());
            }
        }
    }

    public interface ItemClickCallBack {
        public void onItemClicked(String gender);
    }

}
