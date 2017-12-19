package com.example.owner.fatih_kamal_mapd711_onlinepurchase;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.fatih_kamal_mapd711_onlinepurchase.data.DatabaseHelper;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.data.PurchaseContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogin_Click(View view)
    {
        RadioGroup rg = (RadioGroup) findViewById(R.id.rgUT);
        RadioButton rb = (RadioButton)findViewById(rg.getCheckedRadioButtonId());

        DatabaseHelper helper = new DatabaseHelper(this);

        boolean IsCustomer =
                rb.getText().toString() == getResources().getString(R.string.radio_Customer);

        boolean vaildCredential = IsCredentialsValid(helper, IsCustomer);

        if (vaildCredential)
        {
            SharedPreferences sharePref = getSharedPreferences("user", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharePref.edit();
            editor.putString(getResources().getString(R.string.sp_user), ((EditText)findViewById(R.id.usernameInput)).getText().toString());
            editor.putBoolean(getResources().getString(R.string.sp_is_customer), IsCustomer);
            editor.apply();

            Toast.makeText(this.getApplicationContext(), "Sign in successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean IsCredentialsValid(DatabaseHelper helper, boolean IsCustomer)
    {
        EditText username = (EditText)findViewById(R.id.usernameInput);
        EditText pwd = (EditText)findViewById(R.id.passwordInput);

        if(username.getText().toString().trim().length() == 0 ||
                pwd.getText().toString().trim().length() == 0)
        {
            return false;
        }

        Cursor c;
        if (IsCustomer) {

            String[] Projection = {PurchaseContract.CustomerEntry._ID};
            String Selection = PurchaseContract.CustomerEntry.COLUMN_userName + " = ? AND " +
                    PurchaseContract.CustomerEntry.COLUMN_password + " = ?";
            String[] SelectionArgs = { username.getText().toString(),
                                        pwd.getText().toString() };

            c = helper.GetData(
                    PurchaseContract.CustomerEntry.TABLE_NAME,
                    Projection,
                    Selection,
                    SelectionArgs
                    );

        } else {

            String[] Projection = {PurchaseContract.ClerkEntry._ID};
            String Selection = PurchaseContract.ClerkEntry.COLUMN_userName + " = ? AND " +
                    PurchaseContract.ClerkEntry.COLUMN_password + " = ?";
            String[] SelectionArgs = { username.getText().toString(),
                    pwd.getText().toString() };

            c = helper.GetData(
                    PurchaseContract.ClerkEntry.TABLE_NAME,
                    Projection,
                    Selection,
                    SelectionArgs
                    );
        }

        if(c.getCount() > 0)
            return true;
        else
            return false;
    }

    public void Read(SQLiteDatabase db)
    {
        String[] Projection = {
                PurchaseContract.ClerkEntry.COLUMN_userName
        };

        String Selection = null;
        String[] SelectionArgs = null;

        Cursor c = db.query(PurchaseContract.ClerkEntry.TABLE_NAME,
                Projection, Selection, SelectionArgs, null,null,null);

        int i = c.getCount();
        Log.d("Record Count", String.valueOf(i));
    }
}
