package com.quyenlx.learningjapanese.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.quyenlx.learningjapanese.R;
import com.quyenlx.learningjapanese.model.GenderInfo;

import java.util.List;

/**
 * Created by LxQuyen on 20/08/2016.
 */
public class GenderRecyclerViewAdapter extends RecyclerView.Adapter<GenderRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = GenderRecyclerViewAdapter.class.getSimpleName();
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;
    private final Context mContext;
    private List<GenderInfo> mList;

    public GenderRecyclerViewAdapter(Context context, List<GenderInfo> relation) {
        this.mList = relation;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_relation, parent, false);
        return new ViewHolder(v);
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.checkBox.setChecked(mList.get(position).isSelected());
        holder.checkBox.setTag(new Integer(position));
        holder.checkBox.setText(mList.get(position).getTitle());

        if (mList.get(position).isSelected() && holder.checkBox.isChecked()) {
            lastChecked = holder.checkBox;
            lastCheckedPos = position;
        }
        int width = mContext.getResources().getDisplayMetrics().widthPixels;//ConvertUtils.convertPixelsToDp(mContext.getResources().getDisplayMetrics().widthPixels, mContext)
        Log.i(TAG, "=========>Width:" + width);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                width,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 0);
        holder.ll_root_item.setLayoutParams(params);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*CheckBox cb = (CheckBox) v.findViewById(R.id.cb_relation);
                int clickedPos = ((Integer) cb.getTag()).intValue();

                if (cb.isChecked()) {
                    if (lastChecked != null) {
                        lastChecked.setChecked(false);
                        mList.get(lastCheckedPos).setSelected(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                } else {
                    if (lastChecked != null) {
                        if (clickedPos == ((Integer) lastChecked.getTag()).intValue()) {
                            holder.checkBox.setChecked(true);
                            lastChecked = holder.checkBox;
                            lastCheckedPos = clickedPos;
                        } else {
                            lastChecked = null;
                        }
                    }
                    cb.setChecked(true);
                }

                mList.get(clickedPos).setSelected(holder.checkBox.isChecked());*/
                CheckBox cb = (CheckBox) v.findViewById(R.id.cb_relation);
                int clickedPos = ((Integer) cb.getTag()).intValue();

                if (!cb.isChecked()) {
                    if (lastChecked != null) {
                        lastChecked.setChecked(false);
                        mList.get(lastCheckedPos).setSelected(false);
                    }
                    cb.setChecked(true);
                }
                lastChecked = cb;
                lastCheckedPos = clickedPos;
                mList.get(clickedPos).setSelected(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public GenderInfo getGenderSelected() {
        GenderInfo info = null;
        for (GenderInfo relationInfo : mList) {
            if (relationInfo.isSelected()) {
                info = relationInfo;
                break;
            }
        }
        return info;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private LinearLayout ll_root_item;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.cb_relation);
            ll_root_item = (LinearLayout) itemView.findViewById(R.id.ll_root_item);
        }
    }

}