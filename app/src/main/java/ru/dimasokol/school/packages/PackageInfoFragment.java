package ru.dimasokol.school.packages;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

public class PackageInfoFragment extends Fragment {

    public static final String ARG_PACKAGE_NAME = "packageName";

    public static PackageInfoFragment newInstance(String packageName) {
        Bundle args = new Bundle();
        args.putString(ARG_PACKAGE_NAME, packageName);
        PackageInfoFragment fragment = new PackageInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView mApplicationName;
    private ImageView mApplicationIcon;

    public PackageInfoFragment() {
        super(R.layout.fragment_package_info);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  super.onCreateView(inflater, container, savedInstanceState);
        mApplicationName = root.findViewById(R.id.application_name);
        mApplicationIcon = root.findViewById(R.id.package_icon);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showPackageInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(getClass().getName(), "onDestroyView");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(getClass().getName(), "onSaveInstanceState");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getName(), "onDestroy");
    }

    private void showPackageInfo() {
        String packageName = getArguments().getString(ARG_PACKAGE_NAME);
        PackageInfoLoader loader = new PackageInfoLoader(packageName, requireContext(), this);
        loader.execute();
    }

    private void setExtendedInfo(ExtendedInfo info) {
        mApplicationIcon.setImageDrawable(info.mApplicationIcon);
        mApplicationName.setText(info.mApplicationName);
    }

    private static class ExtendedInfo {
        public Drawable mApplicationIcon;
        public String mApplicationName;
    }

    private static class PackageInfoLoader extends AsyncTask<Void, Void, ExtendedInfo> {

        private String mPackageName;
        private Context mContext;
        private WeakReference<PackageInfoFragment> mFragmentRef;

        private PackageInfoLoader(String packageName, Context context, PackageInfoFragment fragment) {
            mPackageName = packageName;
            mContext = context.getApplicationContext();
            mFragmentRef = new WeakReference<>(fragment);
        }

        @Override
        protected ExtendedInfo doInBackground(Void... voids) {

            ExtendedInfo result = new ExtendedInfo();

            try {
                PackageInfo info = mContext.getPackageManager().getPackageInfo(mPackageName, 0);

                result.mApplicationIcon = info.applicationInfo.loadIcon(mContext.getPackageManager());
                result.mApplicationName = info.applicationInfo.loadLabel(mContext.getPackageManager()).toString();

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(ExtendedInfo info) {
            PackageInfoFragment fragment = mFragmentRef.get();

            if (fragment != null && fragment.isAdded()) {
                fragment.setExtendedInfo(info);
            }
        }
    }
}
