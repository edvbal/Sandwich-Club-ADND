package com.example.edvblk.sandwichclubadnd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edvblk.sandwichclubadnd.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textViewOtherNames)
    TextView textViewOtherNames;
    @BindView(R.id.textViewPlace)
    TextView textViewPlace;
    @BindView(R.id.textViewDescription)
    TextView textViewDescription;
    @BindView(R.id.textViewIngredients)
    TextView textViewIngredients;
    public static final String EXTRA_POSITION = "extra_position";
    private static final int POSITION_FAILURE_FLAG = -1;
    private Unbinder unbinder;

    public static void start(Context context, int extra) {
        Intent starter = new Intent(context, DetailActivity.class);
        starter.putExtra(EXTRA_POSITION, extra);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        unbinder = ButterKnife.bind(this);
        closeIfNoIntent();
        int position = getIntent().getIntExtra(EXTRA_POSITION, POSITION_FAILURE_FLAG);
        if (position == POSITION_FAILURE_FLAG) {
            closeOnError();
            return;
        }
        String json = getSandwiches()[position];
        Sandwich sandwich = JsonUtils.getSandwichFromJson(json);
        if (sandwich == null) {
            closeOnError();
            return;
        }
        populateUI(sandwich);
    }

    @NonNull
    private String[] getSandwiches() {
        return getResources().getStringArray(R.array.sandwich_details);
    }

    private void closeIfNoIntent() {
        if (getIntent() == null) {
            closeOnError();
        }
    }

    private void closeOnError() {
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageView);
        setAdditionalNames(sandwich);
        textViewPlace.setText(sandwich.getPlaceOfOrigin());
        textViewDescription.setText(sandwich.getDescription());
        setIngredients(sandwich);
    }

    private void setAdditionalNames(Sandwich sandwich) {
        List<String> names = sandwich.getAlsoKnownAs();
        for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
            textViewOtherNames.append(String.format("%s ", names.get(i)));
            if (i == names.size()) {
                textViewOtherNames.append(names.get(i));
            }
        }
    }

    private void setIngredients(Sandwich sandwich) {
        List<String> ingredients = sandwich.getIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            textViewIngredients.append(String.format("%s ", ingredients.get(i)));
            if (i == ingredients.size()) {
                textViewIngredients.append(ingredients.get(i));
            }
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
