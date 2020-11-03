package com.unilever.julia.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class TagsContainer extends RelativeLayout {

    public TagsContainer(Context context) {
        this(context, null);
    }

    public TagsContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private RecyclerView recyclerView;

    private ContainerChipsAdapter mAdapter;

    public TagsContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.tags_container, this);
        recyclerView = findViewById(R.id.recycler);

        mAdapter = new ContainerChipsAdapter();

        if (isInEditMode()) {
            mAdapter.addChip(new TagsModel("", context.getString(R.string.bulletin_tag_with_territory)));
        }

        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(context)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();
        recyclerView.setLayoutManager(chipsLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mAdapter);
    }

    public interface Listener {
        void onRemoveTag(int count);
    }

    private Listener mListener;

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void addTag(@NonNull TagsModel tags) {
        mAdapter.addChip(tags);
    }

    public void clear() {
        mAdapter.clearAll();
    }

    @NonNull
    public List<TagsModel> getTags() {
        return mAdapter.mData;
    }

    class ContainerChipsAdapter extends RecyclerView.Adapter<ContainerChipsAdapter.ViewHolder> {

        private TagsView getChipView(Context context) {
            return new TagsView(context);
        }

        private List<TagsModel> mData = new ArrayList<>();

        void addChip(TagsModel chip) {
            if (!mData.contains(chip)) {
                mData.add(chip);
                notifyItemInserted(mData.size());
            }
        }

        void removeChip(TagsModel item) {
            if (mData.contains(item)) {
                int position = mData.indexOf(item);
                mData.remove(position);
                notifyItemRemoved(position);
                if (mListener != null) {
                    mListener.onRemoveTag(mData.size());
                }
            }
        }

        void clearAll() {
            mData.clear();
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ContainerChipsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TagsView chipView = getChipView(parent.getContext());
            return new ViewHolder(chipView);
        }

        @Override
        public void onBindViewHolder(@NonNull ContainerChipsAdapter.ViewHolder holder, final int position) {
            final TagsModel tags = mData.get(position);
            holder.chipView.setLabel(tags.getText());
            holder.chipView.setListener(new TagsView.Listener() {
                @Override
                public void onDeleteClick() {
                    removeChip(tags);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TagsView chipView;

            ViewHolder(View view) {
                super(view);
                chipView = (TagsView) view;
            }
        }
    }
}