package com.github.wingadium.search.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.github.wingadium.search.R;
import com.github.wingadium.search.ui.common.Constant;
import com.github.wingadium.search.ui.common.FileUtils;
import com.github.wingadium.search.ui.fragments.MainFragment;

import java.net.URISyntaxException;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            showFileChooser();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    // Get the path
                    String path;
                    try {
                        path = FileUtils.getPath(this, uri);
                    } catch (URISyntaxException e) {
                        path = Constant.STRING_EMPTY;
                    }
                    Log.d(TAG, "File Path: " + path);

                    if (!TextUtils.isEmpty(path)) {
                        Bundle bundl = new Bundle();
                        bundl.putString(Constant.ACTION_FILE_PATH, path);

                        MainFragment mainFragment = new MainFragment();
                        mainFragment.setArguments(bundl);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, mainFragment)
                                .commitAllowingStateLoss();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Open"),
                    Constant.FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
