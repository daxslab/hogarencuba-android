package com.f4adaxs.apps.hogarencuba.activities;import android.content.Intent;import android.net.Uri;import android.os.Bundle;import android.support.design.widget.CollapsingToolbarLayout;import android.support.v7.app.AppCompatActivity;import android.support.v7.widget.Toolbar;import android.view.View;import android.widget.TextView;import android.widget.Toast;import com.nightonke.boommenu.BoomButtons.BoomButton;import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;import com.nightonke.boommenu.BoomButtons.OnBMClickListener;import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;import com.nightonke.boommenu.BoomMenuButton;import com.nightonke.boommenu.ButtonEnum;import com.nightonke.boommenu.Piece.PiecePlaceEnum;import com.f4adaxs.apps.hogarencuba.R;import com.f4adaxs.apps.hogarencuba.util.FontManager;import java.util.ArrayList;import static com.nightonke.boommenu.ButtonEnum.TextInsideCircle;public class DetailsActivity extends AppCompatActivity {    TextView txtTitulo, txtMunicipioProvincia, txtDescripcion, txtBannos, txtCuartos, txtGarage, txtPrecio, txtBed, txtShower, txtCar;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_scrolling);        Toolbar toolbar = findViewById(R.id.toolbar);        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout);        setSupportActionBar(toolbar);        getSupportActionBar().setDisplayHomeAsUpEnabled(true);        String code = getIntent().getExtras().getString("code");        String title = getIntent().getExtras().getString("title");        String bedroomsCount = getIntent().getExtras().getString("bedroomsCount");        String bathroomsCount = getIntent().getExtras().getString("bathroomsCount");        String garageCount = getIntent().getExtras().getString("garageCount");        String areaCount = getIntent().getExtras().getString("areaCount");        String metadata = getIntent().getExtras().getString("metadata");        String description = getIntent().getExtras().getString("description");        String location = getIntent().getExtras().getString("location");        String price = getIntent().getExtras().getString("price");        String ranking = getIntent().getExtras().getString("ranking");        String created_at = getIntent().getExtras().getString("created_at");        String firstImageUrl = getIntent().getExtras().getString("firstImageUrl");        String type = getIntent().getExtras().getString("type");        toolbarLayout.setTitle(code);        txtDescripcion = findViewById(R.id.txtDescripcion);        txtMunicipioProvincia = findViewById(R.id.txtMunicipioProvincia);        txtTitulo = findViewById(R.id.txtTitulo);        txtGarage = findViewById(R.id.txtGarage);        txtPrecio = findViewById(R.id.txtPrecio);        txtCuartos = findViewById(R.id.txtCuartos);        txtBannos = findViewById(R.id.txtBannos);        txtBed = findViewById(R.id.fa_bed);        txtShower = findViewById(R.id.fa_shower);        txtCar = findViewById(R.id.fa_car);        txtDescripcion.setText(description);        txtTitulo.setText(title);        txtBed.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));        txtShower.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));        txtCar.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));        txtMunicipioProvincia.setText(location);        txtBannos.setText(bathroomsCount);        txtCuartos.setText(bedroomsCount);        txtPrecio.setText("Precio: $ " + price);        txtGarage.setText(garageCount);        BoomMenuButton rightBmb = toolbar.findViewById(R.id.action_bar_right_bmb);        for (int i = 0; i < rightBmb.getButtonPlaceEnum().buttonNumber(); i++) {            int color = getResources().getColor(R.color.whiteBackground);            int icon = R.drawable.icon;            switch (i) {                case 0:                    color = getResources().getColor(R.color.red_box);                    icon = R.mipmap.ic_call;                    break;                case 1:                    color = getResources().getColor(R.color.blue_box);                    icon = R.mipmap.ic_sms;                    break;                case 2:                    color = getResources().getColor(R.color.whiteBackground);                    icon = R.mipmap.ic_email;                    break;            }            rightBmb.addBuilder(new TextInsideCircleButton.Builder()                    .normalImageRes(icon).normalColor(color).listener(new OnBMClickListener() {                        @Override                        public void onBoomButtonClick(int index) {                            switch (index) {                                case 0:                                    Intent in = new Intent(Intent.ACTION_DIAL);                                    in.setData(Uri.parse("tel:+5352381595"));                                    startActivity(in);                                    break;                                case 1:                                    Intent intentSMS = new Intent("android.intent.action.VIEW");                                    intentSMS.setData(Uri.parse("sms:+5352381595"));                                    intentSMS.putExtra(Intent.EXTRA_TEXT,"Sobre la " + code);                                    startActivity(intentSMS);                                    break;                                case 2:                                    Intent intent = new Intent(Intent.ACTION_SEND);                                    String[] email = {"contact@hogarencuba.com"};                                    intent.putExtra(Intent.EXTRA_EMAIL,email);                                    intent.putExtra(Intent.EXTRA_SUBJECT,"Sobre la propiedad " + code);                                    intent.setType("message/rfc822");                                    Intent chooser = Intent.createChooser(intent,"Email");                                    startActivity(chooser);                                    break;                            }                        }                    }).pieceColor(getResources().getColor(R.color.whiteBackground)));        }    }}