package com.example.android.smarthashtaggenerator;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.smarthashtaggenerator.utils.MicrosoftComputerVisionUtils;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by benny on 3/16/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ItemResultViewHolder> {

    private ArrayList<MicrosoftComputerVisionUtils.ComputerVisionItem> mItemResultList;
    private ImageView image;
    private TextView hashes;
    private String hashText;
    private MicrosoftComputerVisionUtils.ComputerVisionItem visionItem;
    private File file;
    private ArrayList<String> tagList;

    private OnItemClickListener mOnItemClickListener;

    private final static String TAG  = Adapter.class.getSimpleName();

    public Adapter(OnItemClickListener onItemClickListener) {
        mItemResultList = new ArrayList<MicrosoftComputerVisionUtils.ComputerVisionItem>();
        mOnItemClickListener = onItemClickListener;
    }

    public void updateResults(ArrayList<MicrosoftComputerVisionUtils.ComputerVisionItem> itemResults) {
        mItemResultList = itemResults;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mItemResultList != null) {
            return mItemResultList.size();
        } else {
            return 0;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MicrosoftComputerVisionUtils.ComputerVisionItem visionItem);
    }

    public ItemResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.picture_item, parent, false);
        return new ItemResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemResultViewHolder holder, int position) {
        holder.bind(mItemResultList.get(position));
    }

    class ItemResultViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mItemLL;
        private TextView mItemResultTV;
        private ImageView mPictureResultIV;

        public ItemResultViewHolder(View itemView) {
            super(itemView);
            mItemLL = (LinearLayout) itemView.findViewById(R.id.ll_item);
            mPictureResultIV = (ImageView)itemView.findViewById(R.id.iv_image_result);
            mItemResultTV = (TextView)itemView.findViewById(R.id.tv_item_result);
        }

        public void bind(MicrosoftComputerVisionUtils.ComputerVisionItem itemResult) {
            tagList = new ArrayList<String>();

            visionItem = itemResult;
            file = visionItem.file;
            tagList = visionItem.tags;

            String hashText = "";

            Bitmap photoBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            for (String item : tagList) {
                Log.d(TAG, "item: " + item);
                Log.d(TAG, "hashText: " + hashText);
                hashText += item + " ";
            }

            mPictureResultIV.setImageBitmap(photoBitmap);
            mItemResultTV.setText(hashText);

            mItemLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(mItemResultList.get(getAdapterPosition()));
                }
            });
        }
    }
}
