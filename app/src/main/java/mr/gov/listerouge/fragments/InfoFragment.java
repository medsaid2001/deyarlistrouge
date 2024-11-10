package mr.gov.listerouge.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import mr.gov.listerouge.R;

public class InfoFragment extends Fragment {

    private TextView textViewDeviceSn;
    private TextView textViewDeviceBrand;
    private TextView textViewDeviceManufacturer;
    private TextView textViewDeviceModel;
    private TextView textViewDeviceName;
    private TextView textViewDeviceSystemName;
    private TextView textViewDeviceSystemVersion;
    private TextView textViewAppName;
    private TextView textViewAppVersion;
    private TextView textViewPhoneNumber;
    private SharedPreferences sharedPreferences;
    public static String serial;
    public static String structure;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        if(sharedPreferences==null){
            sharedPreferences=  getContext().getSharedPreferences("data", MODE_PRIVATE);
        }
        structure = sharedPreferences.getString("structure","N/A");
        serial = sharedPreferences.getString("serial","N/A");initializeViews(view);
        displayDeviceInfo(view);

        return view;
    }

    private void initializeViews(View view) {
        textViewDeviceSn = view.findViewById(R.id.textViewSerialNumber);
        textViewDeviceBrand = view.findViewById(R.id.textViewBrand);
        textViewDeviceManufacturer = view.findViewById(R.id.textViewManufacturer);
        textViewDeviceModel = view.findViewById(R.id.textViewModel);
        textViewDeviceName = view.findViewById(R.id.textViewDeviceName);
        textViewDeviceSystemName = view.findViewById(R.id.textViewSystemName);
        textViewDeviceSystemVersion = view.findViewById(R.id.textViewSystemVersion);
        textViewAppName = view.findViewById(R.id.textViewAppName);
        textViewAppVersion = view.findViewById(R.id.textViewVersion);
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber);
    }

    private void displayDeviceInfo(View view) {
        String last10n = getLastTenChars(serial);
        textViewDeviceSn.setText("****"+last10n);
        textViewDeviceBrand.setText(Build.BRAND);
        textViewDeviceManufacturer.setText(Build.MANUFACTURER);
        textViewDeviceModel.setText(Build.MODEL);
        textViewDeviceName.setText(Build.DEVICE);
        textViewDeviceSystemName.setText("Android");
        textViewDeviceSystemVersion.setText(Build.VERSION.RELEASE);
        textViewAppName.setText(getAppName(view.getContext()));
        textViewAppVersion.setText(getAppVersion(view.getContext()));
        textViewPhoneNumber.setText(structure);
    }

    private String getAppName(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    private String getAppVersion(Context context) {
        String version = "Unknown";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pInfo = pm.getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    private String getPhoneNumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tMgr != null && context.checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            return tMgr.getLine1Number(); // This may require permission
        }
        return "Unknown";
    }
    public static String getLastTenChars(String input) {
        if (input == null) {
            return ""; // Handle null input if needed
        }

        int length = input.length();
        if (length <= 10) {
            return input; // Return the whole string if it's less than or equal to 10 characters
        }

        return input.substring(length - 10); // Get the last 10 characters
    }
}
