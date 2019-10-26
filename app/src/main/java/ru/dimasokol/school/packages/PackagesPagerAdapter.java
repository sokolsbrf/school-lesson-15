package ru.dimasokol.school.packages;

import android.content.Context;
import android.content.pm.PackageInfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.Collections;
import java.util.List;

public class PackagesPagerAdapter extends FragmentStatePagerAdapter {
    private List<PackageInfo> mPackages = Collections.emptyList();

    public PackagesPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return PackageInfoFragment.newInstance(mPackages.get(position).packageName);
    }

    @Override
    public int getCount() {
        return mPackages.size();
    }

    public void updatePackages(@NonNull Context context) {
        mPackages = context.getPackageManager().getInstalledPackages(0);
    }
}
