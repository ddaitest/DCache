package com.ddai.mice;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.ddai.mice.manager.Source;
import com.ddai.mice.manager.TestManager;
import com.sunny.cache.BaseLoadListener;
import com.sunny.cache.CacheWorker;
import com.sunny.cache.ThumbnailLoader;

import java.util.ArrayList;

/**
 * Created by ningdai on 14-8-7.
 */
public class ImageListFragment extends Fragment {

    ListView listView;
    TestAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("DDAI", "on Create");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("DDAI", "on Create View");
        View base = inflater.inflate(R.layout.imagelist, container, false);
        listView = (ListView) base.findViewById(R.id.listView);
        adapter = new TestAdapter(TestManager.getURL(), inflater, getActivity().getApplicationContext());
        listView.setAdapter(adapter);
        return base;
    }

    private static class TestAdapter extends BaseAdapter {
        ArrayList<Source> sources;
        LayoutInflater inflater;
        ThumbnailLoader loader;

        public TestAdapter(ArrayList<Source> sources, LayoutInflater inflater, Context context) {
            this.sources = sources;
            this.inflater = inflater;
            loader = ThumbnailLoader.getInstance(context, "");
        }

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            return sources.size();
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         *                 data set.
         * @return The data at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return sources.get(position);
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * Get a View that displays the data at the specified position in the data set. You can either
         * create a View manually or inflate it from an XML layout file. When the View is inflated, the
         * parent View (GridView, ListView...) will apply default layout parameters unless you use
         * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
         * to specify a root view and to prevent attachment to the root.
         *
         * @param position    The position of the item within the adapter's data set of the item whose view
         *                    we want.
         * @param convertView The old view to reuse, if possible. Note: You should check that this view
         *                    is non-null and of an appropriate type before using. If it is not possible to convert
         *                    this view to display the correct data, this method can create a new view.
         *                    Heterogeneous lists can specify their number of view types, so that this View is
         *                    always of the right type (see {@link #getViewTypeCount()} and
         *                    {@link #getItemViewType(int)}).
         * @param parent      The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder holder;
            if (v == null) {

                v = inflater.inflate(R.layout.imageitem, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) v.findViewById(R.id.imageView);
                holder.text = (TextView) v.findViewById(R.id.textView);
                v.setTag(holder);
            }
            holder = (ViewHolder) v.getTag();
            Source s = sources.get(position);
            loader.loadRemoteImage(s.url, holder.image, new CacheWorker.Builder(0, 0), new BaseLoadListener(holder.image));
            holder.text.setText(s.title);
            return v;
        }

        private static class ViewHolder {
            ImageView image;
            TextView text;
        }
    }
}
