package id.co.kosankoding.angkotbandung.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.kosankoding.angkotbandung.R;
import id.co.kosankoding.angkotbandung.adapter.AngkotAdapter;
import id.co.kosankoding.angkotbandung.model.Angkot;
import id.co.kosankoding.angkotbandung.model.ResponseAngkot;

import static id.co.kosankoding.angkotbandung.utils.Constant.BASE_URL;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_angkot)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    SearchView searchView;
    List<Angkot> listAngkot;
    AngkotAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_judul);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());

        initRecylerView();
    }

    private void initRecylerView() {
        listAngkot = new ArrayList<>();
        adapter = new AngkotAdapter(this, listAngkot);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.hasFixedSize();

        loadItems();
    }

    private void loadItems() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.get(BASE_URL + "angkots/")
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(ResponseAngkot.class, new ParsedRequestListener<ResponseAngkot>() {
                    @Override
                    public void onResponse(ResponseAngkot response) {
                        List<Angkot> data = response.getData();
                        if (data != null) {
                            Log.e("RESPONSE", "" + response.toString());
                            listAngkot.addAll(data);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MainActivity.this, "Data kosong !", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MainActivity.this, "Cannot load data !", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_search, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();

        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        return super.onOptionsItemSelected(item);
    }
}
