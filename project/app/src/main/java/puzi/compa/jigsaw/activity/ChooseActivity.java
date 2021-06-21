package puzi.compa.jigsaw.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.theartofdev.edmodo.cropper.CropImage;

import puzi.compa.adapter.CommonRecyclerViewAdapter;
import puzi.compa.adapter.ViewHolder;
import puzi.compa.R;
import puzi.compa.jigsaw.util.MyLog;
import puzi.compa.jigsaw.util.ResUtils;
import puzi.compa.jigsaw.view.SquareGridSpacingItemDecoration;

public class ChooseActivity extends AppCompatActivity {


    private static final String TAG = "ChooseActivity";
    private static final int CHOOSER_SPAN_COUNT = 3;

    private int[] mResIds = PictureIDUtil.getImgIDs();
    private Uri[] mUris = new Uri[mResIds.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

            for (int i = 0; i < mResIds.length; i++) {
                mUris[i] = ResUtils.getUriOfResource(this, mResIds[i]);
            }

            initView();
    }

    private void initView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_pics);
        assert recyclerView != null;

        CommonRecyclerViewAdapter<Uri> adapter = new CommonRecyclerViewAdapter<Uri>(this, mUris, R.layout.choose_pic_item) {
            @Override
            public void onItemViewAppear(ViewHolder holder, Uri uri, int position) {
                holder.setViewImageResource(R.id.iv_image, mResIds[position]);
            }
        };



        adapter.setOnItemClickListener(new CommonRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                returnUri(mUris[position]);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, CHOOSER_SPAN_COUNT));
        recyclerView.addItemDecoration(new SquareGridSpacingItemDecoration(this, R.dimen.brick_divider_width, CHOOSER_SPAN_COUNT));
    }



    public void chooseFromGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE: {
                if (resultCode == RESULT_OK) {
                    CropImage.activity(data.getData())
                            .setActivityTitle(getString(R.string.crop))
                            .setAspectRatio(1, 1)
                            .setFixAspectRatio(true)
                            .start(this);
                }
                break;
            }
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE: {
                if (resultCode == RESULT_OK) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    returnUri(result.getUri());
                }
                break;
            }
        }
    }

    private void returnUri(Uri uri) {
        Intent intent = new Intent();
        intent.setData(uri);
        setResult(RESULT_OK, intent);
        finish();
    }
}
