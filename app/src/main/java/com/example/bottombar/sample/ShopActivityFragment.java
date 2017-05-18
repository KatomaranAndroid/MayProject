/*
 * Copyright (c) 2017. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.example.bottombar.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bottombar.sample.shop.Item;
import com.example.bottombar.sample.shop.Shop;
import com.example.bottombar.sample.shop.ShopAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.Orientation;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

public class ShopActivityFragment extends Fragment implements DiscreteScrollView.OnItemChangedListener,
        View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private View rootview;
    private List<Item> data;
    private Shop shop;
    private GoogleMap mMap;

    //    private TextView currentItemName;
//    private TextView currentItemPrice;
//    private ImageView rateItemButton;
    private DiscreteScrollView itemPicker;
    private InfiniteScrollAdapter infiniteAdapter;

    public static ShopActivityFragment newInstance() {
        ShopActivityFragment fragment = new ShopActivityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_shop, container, false);

//        currentItemName = (TextView) rootview.findViewById(R.id.item_name);
//        currentItemPrice = (TextView) rootview.findViewById(R.id.item_price);
//        rateItemButton = (ImageView) rootview.findViewById(R.id.item_btn_rate);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        shop = Shop.get();
        data = shop.getData();
        itemPicker = (DiscreteScrollView) rootview.findViewById(R.id.item_picker);
        itemPicker.setOrientation(Orientation.HORIZONTAL);
        itemPicker.addOnItemChangedListener(this);
        infiniteAdapter = InfiniteScrollAdapter.wrap(new ShopAdapter(data));
        itemPicker.setAdapter(infiniteAdapter);
        itemPicker.setItemTransitionTimeMillis(DiscreteScrollViewOptions.getTransitionTime());
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(1.0f)
                .build());

        onItemChanged(data.get(0));

//        rootview. findViewById(R.id.item_btn_rate).setOnClickListener(this);
//        rootview.findViewById(R.id.item_btn_buy).setOnClickListener(this);
//        rootview. findViewById(R.id.item_btn_comment).setOnClickListener(this);

        rootview.findViewById(R.id.home).setOnClickListener(this);
//        rootview.findViewById(R.id.btn_smooth_scroll).setOnClickListener(this);
//        rootview. findViewById(R.id.btn_transition_time).setOnClickListener(this);

        return rootview;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_btn_rate:
                Item current = data.get(itemPicker.getCurrentItem());
                shop.setRated(current.getId(), !shop.isRated(current.getId()));
                changeRateButtonState(current);
                break;
            case R.id.home:
                getActivity().finish();
                break;
            case R.id.btn_transition_time:
                DiscreteScrollViewOptions.configureTransitionTime(itemPicker);
                break;
            case R.id.btn_smooth_scroll:
                DiscreteScrollViewOptions.smoothScrollToUserSelectedPosition(itemPicker, v);
                break;
            default:
                showUnsupportedSnackBar();
                break;
        }
    }

    private void onItemChanged(Item item) {
        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.zoomIn());

            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new
                            LatLng(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLon())))
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void changeRateButtonState(Item item) {
//        if (shop.isRated(item.getId())) {
//            rateItemButton.setImageResource(R.drawable.ic_star_black_24dp);
//            rateItemButton.setColorFilter(ContextCompat.getColor(getActivity(), R.color.shopRatedStar));
//        } else {
//            rateItemButton.setImageResource(R.drawable.ic_star_border_black_24dp);
//            rateItemButton.setColorFilter(ContextCompat.getColor(getActivity(), R.color.shopSecondary));
//        }
    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int position) {
        int positionInDataSet = infiniteAdapter.getRealPosition(position);
        onItemChanged(data.get(positionInDataSet));
    }

    private void showUnsupportedSnackBar() {
        Snackbar.make(itemPicker, R.string.msg_unsupported_op, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMarkerClickListener(this);
        for (int i = 0; i < data.size(); i++) {
            mMap.addMarker(new MarkerOptions().position(new
                    LatLng(Double.parseDouble(data.get(i).getLat()), Double.parseDouble(data.get(i).getLon()))));
        }
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new
                        LatLng(Double.parseDouble(data.get(0).getLat()), Double.parseDouble(data.get(0).getLon())))
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getLat().equals(String.valueOf(marker.getPosition().latitude)) &&
                    data.get(i).getLon().equals(String.valueOf(marker.getPosition().longitude))) {
                int destination = i;
                if (itemPicker.getAdapter() instanceof InfiniteScrollAdapter) {
                    destination = ((InfiniteScrollAdapter) itemPicker.getAdapter()).getClosestPosition(destination);
                }
                itemPicker.smoothScrollToPosition(destination);
            }
        }
        return false;
    }
}
