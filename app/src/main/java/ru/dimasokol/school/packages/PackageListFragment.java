package ru.dimasokol.school.packages;


import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Список пакетов на устройстве
 */
public class PackageListFragment extends Fragment {

    public PackageListFragment() {
        super(R.layout.fragment_package_list);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView root = (RecyclerView) super.onCreateView(inflater, container, savedInstanceState);
        PackagesAdapter adapter = new PackagesAdapter(new PackagesAdapter.OnPackageClickListener() {
            @Override
            public void onPackageClick(PackageInfo info) {
                if (getActivity() instanceof PackageInfoHolder) {
                    ((PackageInfoHolder) getActivity()).showPackageInfo(info.packageName);
                }
            }
        });
        adapter.updatePackages(requireContext());
        root.setAdapter(adapter);
        return root;
    }

}
