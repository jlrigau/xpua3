package com.android.xpua;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import com.android.xpua.common.BaseFragmentActivity;
import com.android.xpua.injection.Injector;
import com.android.xpua.injection.annotation.FragmentById;
import com.android.xpua.model.Artist;
import com.android.xpua.service.rest.JsonRestService;

public class HomeActivity extends BaseFragmentActivity {

    private ResultReceiver resultReceiver = new ResultReceiver(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what > 0){

            }
        }
    });

    @FragmentById(R.id.map)
    LocationMapFragment locationMapFragment;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the Above View
        setContentView(R.layout.main);
        Injector.injectViews(this);
        Intent intent = new Intent(this, JsonRestService.class);
        intent.setData(Uri.parse("http://192.168.0.41/test.json"));
        intent.putExtra(JsonRestService.EXTRA_RESULT_RECEIVER, resultReceiver);
        intent.putExtra(JsonRestService.EXTRA_JSON_CLASS, new Artist());
        startService(intent);
        if(savedInstanceState == null){
            // when notification push done
            //startService(new Intent(this, GcmRegistrationService.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        final MenuItem searchItem = menu.findItem(R.id.menu_search);
        if (searchItem != null ) {
            SearchView searchView = (SearchView) searchItem.getActionView();
            if (searchView != null) {
                SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        locationMapFragment.updateLocationOnMap(s);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });
            }
        }
        return true;
    }
}
