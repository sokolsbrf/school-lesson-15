package ru.dimasokol.school.packages;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.Holder> {

    private LayoutInflater mInflater;
    private List<PackageInfo> mPackages = Collections.emptyList();
    private final OnPackageClickListener mItemClickListener;

    public PackagesAdapter(OnPackageClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        return new Holder(mInflater.inflate(R.layout.item_package, parent, false), mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mPackages.get(position));
    }

    @Override
    public int getItemCount() {
        return mPackages.size();
    }

    public void updatePackages(@NonNull Context context) {
        mPackages = context.getPackageManager().getInstalledPackages(0);
    }

    protected static class Holder extends RecyclerView.ViewHolder {

        private TextView mPackageName;
        private final OnPackageClickListener mListener;

        public Holder(@NonNull View itemView, OnPackageClickListener listener) {
            super(itemView);
            mPackageName = (TextView) itemView;
            mListener = listener;
        }

        public void bind(final PackageInfo info) {
            mPackageName.setText(info.packageName);
            mPackageName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onPackageClick(info);
                }
            });
        }
    }

    public interface OnPackageClickListener {
        void onPackageClick(PackageInfo info);
    }
}
