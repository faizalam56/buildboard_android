package com.buildboard.modules.signup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardButton;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.dialogs.PopUpHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.login.LoginActivity;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerData;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerRequest;
import com.buildboard.permissions.PermissionHelper;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.StringUtils;
import com.buildboard.utils.Utils;
import com.buildboard.view.SnackBarFactory;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import java.io.File;
import java.io.IOException;
import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import static com.buildboard.utils.Utils.getImageUri;
import static com.buildboard.utils.Utils.resizeAndCompressImageBeforeSend;
import static com.buildboard.utils.Utils.selectImage;
import static com.buildboard.utils.Utils.showProgressColor;

public class SignUpActivity extends AppCompatActivity implements AppConstant {

    private final String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    private final int PICK_IMAGE_CAMERA = 2001;
    private final int PICK_IMAGE_GALLERY = 2002;
    private LatLng addressLatLng;
    private String mProvider;
    private String mProviderId;
    private String mEmail;
    private String mFirstName;
    private String mLastName;
    private Uri selectedImage;
    private Bitmap bitmap;
    private String contactMode = PHONE;


    @BindView(R.id.radio_group_contact_mode)
    RadioGroup radioGroupContactMode;
    @BindView(R.id.radio_phone)
    RadioButton radioPhone;
    @BindView(R.id.radio_email)
    RadioButton radioEmail;
    @BindView(R.id.image_profile)
    ImageView imageProfile;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.text_add_profile_picture)
    BuildBoardTextView textAddProfilePicture;
    @BindView(R.id.text_first_name)
    BuildBoardTextView textFirstName;
    @BindView(R.id.text_last_name)
    BuildBoardTextView textLastName;
    @BindView(R.id.text_email)
    BuildBoardTextView textEmail;
    @BindView(R.id.text_password)
    BuildBoardTextView textPassword;
    @BindView(R.id.text_address)
    BuildBoardTextView textAddress;
    @BindView(R.id.text_phone)
    BuildBoardTextView textPhone;
    @BindView(R.id.text_terms_of_service)
    BuildBoardTextView textTermsOfService;
    @BindView(R.id.edit_first_name)
    BuildBoardEditText editFirstName;
    @BindView(R.id.edit_last_name)
    BuildBoardEditText editLastName;
    @BindView(R.id.edit_address)
    BuildBoardTextView editAddress;
    @BindView(R.id.edit_phoneno)
    BuildBoardEditText editPhoneNo;
    @BindView(R.id.text_contact_mode)
    BuildBoardTextView textContactMode;
    @BindView(R.id.edit_email)
    BuildBoardEditText editEmail;
    @BindView(R.id.edit_password)
    BuildBoardEditText editPassword;
    @BindView(R.id.button_next)
    BuildBoardButton buttonNext;
    @BindView(R.id.constraint_consumer_address_container)
    ConstraintLayout constraintConsumerAddressContainer;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.input_layout_password)
    TextInputLayout textInputLayout;

    @BindString(R.string.gender)
    String stringGender;
    @BindString(R.string.female)
    String stringFemale;
    @BindString(R.string.male)
    String stringMale;
    @BindString(R.string.other)
    String stringOther;
    @BindString(R.string.preferred_contact_mode)
    String stringPreferredContactMode;
    @BindString(R.string.working_area)
    String stringWorkingArea;
    @BindString(R.string.user_type)
    String stringUserType;
    @BindString(R.string.contractor)
    String stringContractor;
    @BindString(R.string.consumer)
    String stringConsumer;
    @BindString(R.string.error_enter_first_name)
    String stringErrorFirstName;
    @BindString(R.string.error_enter_last_name)
    String stringErrorLastName;
    @BindString(R.string.error_invalid_email)
    String stringErrorInvalidEmail;
    @BindString(R.string.error_enter_email)
    String stringErrorEmail;
    @BindString(R.string.error_password)
    String stringErrorPasswordEmptyMsg;
    @BindString(R.string.error_enter_business_name)
    String stringErrorBusinessName;
    @BindString(R.string.error_enter_business_address)
    String stringErrorBusinessAddress;
    @BindString(R.string.error_enter_summary)
    String stringSummary;
    @BindString(R.string.error_enter_address)
    String stringErrorAddress;
    @BindString(R.string.error_enter_valid_phone_number)
    String stringErrorValidPhoneNumber;
    @BindString(R.string.error_enter_phone_number)
    String stringErrorPhoneNumber;
    @BindString(R.string.error_select_user_type)
    String stringErrorSelectUserType;
    @BindString(R.string.error_first_name_short)
    String stringErrorFirstnameTooShort;
    @BindString(R.string.error_last_name_short)
    String stringErrorLastnameTooShort;
    @BindString(R.string.error_incorrect_password_length)
    String stringErrorPasswordLength;
    @BindString(R.string.error_user_type)
    String stringErrorUserType;
    @BindString(R.string.error_working_area)
    String stringErrorWorkingArea;
    @BindString(R.string.error_summary)
    String stringErrorSummary;
    @BindString(R.string.sign_up)
    String stringSignUp;
    @BindString(R.string.error_contractor_type)
    String stringErrorContractorType;
    @BindString(R.string.phone)
    String stringPhone;
    @BindString(R.string.email)
    String stringEmail;
    @BindString(R.string.terms_of_service)
    String stringTermsOfService;
    @BindString(R.string.privacy_policy_text)
    String stringPrivacyPolicy;
    @BindString(R.string.please_enter_a_valid_address)
    String stringEnterValidAddress;
    @BindString(R.string.msg_please_wait)
    String stringPleaseWait;
    @BindString(R.string.please_select_image)
    String stringSelectImage;
    @BindString(R.string.location_check)
    String stringCheckLocation;
    @BindString(R.string.special_charactor)
    String blockCharacterSet;
    @BindArray(R.array.user_type_array)
    String[] arrayUserType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        showProgressColor(this, progressBar);
        title.setText(stringSignUp);
        setAsteriskToText();
        setTermsServiceText();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionHelper permission = new PermissionHelper(this);
            if (!permission.checkPermission(permissions))
                requestPermissions(permissions, REQUEST_PERMISSION_CODE);
        }

        getIntentData();

        radioGroupContactMode.setOnCheckedChangeListener(checkedChangeListener);
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setAsteriskToText() {
        textFirstName.setText(Utils.setStarToLabel(getString(R.string.first_name)));
        textLastName.setText(Utils.setStarToLabel(getString(R.string.last_name)));
        textEmail.setText(Utils.setStarToLabel(getString(R.string.email)));
        textPassword.setText(Utils.setStarToLabel(getString(R.string.password)));
        textAddress.setText(Utils.setStarToLabel(getString(R.string.address)));
        textPhone.setText(Utils.setStarToLabel(getString(R.string.phone_no)));
        textContactMode.setText(Utils.setStarToLabel(getString(R.string.preferred_contact_mode)));
    }

    @OnClick(R.id.edit_address)
    void consumerAddressTapped() {
        if (ConnectionDetector.isNetworkConnected(this)) {
            try {
                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                Intent intent = intentBuilder.build(this);
                startActivityForResult(intent, PLACE_PICKER_REQUEST);

            } catch (GooglePlayServicesRepairableException
                    | GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        } else {
            ConnectionDetector.createSnackBar(this, constraintRoot);
        }
    }

    @OnClick(R.id.button_next)
    void registerButtonTapped() {
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString().trim();
        String address = editAddress.getText().toString();
        String phoneNo = editPhoneNo.getText().toString();

        if (ConnectionDetector.isNetworkConnected(this)) {
            if (validateFields(firstName, lastName, email, password, address, phoneNo)) {
                if (selectedImage == null) {
                    SnackBarFactory.createSnackBar(this, constraintRoot, stringSelectImage);
                } else {
                    uploadImage(this, prepareFilePart(resizeAndCompressImageBeforeSend(this,Utils.getImagePath(this, selectedImage))));
                }
            }
        } else {
            ConnectionDetector.createSnackBar(this, constraintRoot);
        }
    }

    @OnClick({R.id.image_profile, R.id.text_add_profile_picture})
    void imageProfileTapped() {
        if (ConnectionDetector.isNetworkConnected(this)) {
            selectImage(this);
        } else {
            ConnectionDetector.createSnackBar(this, constraintRoot);
        }
    }

    private void signUpMethod(String firstName, String lastName, String email, String password, String address, String phoneNo, String contactMode, String imageUrl) {
        createConsumer(firstName, lastName, email, password, address, phoneNo, contactMode, imageUrl);
    }

    private void createConsumer(String firstName, String lastName, String email, String password, String address, String phoneNo, String contactMode, String imageUrl) {
        CreateConsumerRequest consumerRequest = getConsumerDetails(firstName, lastName, email, password, address, phoneNo, contactMode, imageUrl);
        if (consumerRequest.getLatitude() == null || consumerRequest.getLongitude() == null) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringEnterValidAddress);
            return;
        }

        showProgressBar();
        DataManager.getInstance().createConsumer(SignUpActivity.this, consumerRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                hideProgressBar();
                if (response == null) return;

                CreateConsumerData createConsumerData = (CreateConsumerData) response;
                if (!createConsumerData.getMessage().isEmpty()) {
                    PopUpHelper.showAlertPopup(SignUpActivity.this, createConsumerData.getMessage(), new PopUpHelper.ConfirmPopUp() {
                        @Override
                        public void onConfirm(boolean isConfirm) {
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        }
                        @Override
                        public void onDismiss(boolean isDismiss) { }
                    });
                }
            }

            @Override
            public void onError(Object error) {
                hideProgressBar();
                Utils.showError(SignUpActivity.this, constraintRoot, error);
            }
        });
    }

    private CreateConsumerRequest getConsumerDetails(String firstName, String lastName, String email, String password, String address, String phoneNo, String contactMode, String imageUrl) {
        CreateConsumerRequest consumerRequest = new CreateConsumerRequest();
        consumerRequest.setFirstName(firstName);
        consumerRequest.setLastName(lastName);
        consumerRequest.setEmail(email);
        consumerRequest.setPassword(password);
        consumerRequest.setAddress(address);
        consumerRequest.setPhoneNo(phoneNo);
        consumerRequest.setContactMode(contactMode);

        if (!TextUtils.isEmpty(imageUrl))
            consumerRequest.setImage(imageUrl);

        if (addressLatLng != null) {
            consumerRequest.setLatitude(String.valueOf(addressLatLng.latitude));
            consumerRequest.setLongitude(String.valueOf(addressLatLng.longitude));
        }

        if (!TextUtils.isEmpty(mProvider) && !TextUtils.isEmpty(mProviderId)) {
            consumerRequest.setProvider(mProvider);
            consumerRequest.setProviderId(mProviderId);
        }

        return consumerRequest;
    }

    private void setTermsServiceText() {
        SpannableString styledString = new SpannableString(getString(R.string.privacy_policy_text));
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 34, 50, 0);
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 55, 69, 0);
        styledString.setSpan(clickableSpanTermsService, 34, 50, 0);
        styledString.setSpan(clickableSpanPrivacyPolicy, 55, 69, 0);
        textTermsOfService.setText(styledString);
        textTermsOfService.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private boolean validateFields(String firstName, String lastName, String email, String password, String address, String phoneNo) {

        if (TextUtils.isEmpty(firstName)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorFirstName);
            return false;
        } else if (firstName.length() < 3) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorFirstnameTooShort);
            return false;
        }

        if (TextUtils.isEmpty(lastName)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorLastName);
            return false;
        } else if (lastName.length() < 3) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorLastnameTooShort);
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorEmail).show();
            return false;
        } else if (!StringUtils.isValidEmailId(email)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorInvalidEmail).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorPasswordEmptyMsg).show();
            return false;
        } else if (password.length() < 6) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorPasswordLength).show();
            return false;
        }

        if (TextUtils.isEmpty(address)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorAddress).show();
            return false;
        }

        if (TextUtils.isEmpty(phoneNo)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorPhoneNumber).show();
            return false;
        } else if (phoneNo.length() < 9){
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorValidPhoneNumber).show();
            return false;
        }

        return true;
    }

    private void createAccount(String imageUrl) {
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString().trim();
        String address = editAddress.getText().toString();
        String phoneNo = editPhoneNo.getText().toString();

        if (validateFields(firstName, lastName, email, password, address, phoneNo)) {
            signUpMethod(firstName, lastName, email, password, address, phoneNo, contactMode, imageUrl);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case IMAGE_UPLOAD_REQUEST_CODE:
                    createAccount(data.getStringExtra(INTENT_IMAGE_URL));
                    break;

                case PLACE_PICKER_REQUEST:
                    getAddressLatLng(PlacePicker.getPlace(this, data));
                    break;

                case PICK_IMAGE_GALLERY:
                    selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        imageProfile.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case PICK_IMAGE_CAMERA:
                    try {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            bitmap = (Bitmap) extras.get("data");
                            selectedImage=  getImageUri(this,bitmap);
                            imageProfile.setImageBitmap(bitmap);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void getAddressLatLng(final Place place) {
        showAddressDialog(place);
        addressLatLng = place.getLatLng();
    }

    private void showAddressDialog(Place place) {
        if (place != null) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") final View dialogView = inflater.inflate(R.layout.dialog_custom_places, null);
        dialogBuilder.setView(dialogView);
        final BuildBoardEditText buildBoardEditText = dialogView.findViewById(R.id.editPlaceName);
        final BuildBoardTextView textView = dialogView.findViewById(R.id.textSelectedLocation);
        buildBoardEditText.setText(place.getName());
        textView.setText(place.getAddress());
        dialogBuilder.setTitle(getString(R.string.confirm_location));
        dialogBuilder.setPositiveButton(getString(R.string.done), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String newLocation = buildBoardEditText.getText().toString();
                String selectedLocation = textView.getText().toString();
                if (TextUtils.isEmpty(newLocation)) {
                    SnackBarFactory.createSnackBar(SignUpActivity.this, constraintRoot, stringCheckLocation);
                } else {
                    editAddress.setText(String.format("%s%s%s", newLocation, getString(R.string.comma), selectedLocation));
                }
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog alertChangeAddressDialog = dialogBuilder.create();
        alertChangeAddressDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertChangeAddressDialog.show();
        }
    }

    public void getIntentData() {
        if (getIntent().hasExtra(INTENT_PROVIDER) && getIntent().hasExtra(INTENT_PROVIDER_ID) && getIntent().hasExtra(INTENT_EMAIL)) {
            mProvider = getIntent().getStringExtra(INTENT_PROVIDER);
            mProviderId = getIntent().getStringExtra(INTENT_PROVIDER_ID);
            mEmail = getIntent().getStringExtra(INTENT_EMAIL);
            mFirstName = getIntent().getStringExtra(INTENT_FIRST_NAME);
            mLastName = getIntent().getStringExtra(INTENT_LAST_NAME);
        }

        if (mProvider != null && mProviderId != null) {
            editEmail.setText(mEmail);
            editFirstName.setText(mFirstName);
            editLastName.setText(mLastName);
            editPassword.setText(IS_LOGIN);
            showActiveState(View.GONE, false);
        } else {
            showActiveState(View.VISIBLE, true);
        }
    }

    public void showActiveState(int view, boolean isVisible){
        editEmail.setFocusable(isVisible);
        editEmail.setFocusableInTouchMode(isVisible);
        editEmail.setClickable(isVisible);
        editEmail.setCursorVisible(isVisible);
        textPassword.setVisibility(view);
        editPassword.setVisibility(view);
        textInputLayout.setVisibility(view);
    }

    ClickableSpan clickableSpanTermsService = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            openLink(TERMS_OF_SERVICES_LINK);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };

    ClickableSpan clickableSpanPrivacyPolicy = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            openLink(PRIVACY_POLICY_LINK);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };

    private void openLink(String link) {
        Uri uri = Uri.parse(link);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            contactMode = radioPhone.isSelected() ? PHONE : EMAIL;
        }
    };

    public void uploadImage(Activity activity, MultipartBody.Part image) {
        showProgressBar();
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), AppPreference.getAppPreference(this).getBoolean(IS_CONTRACTOR) ? getString(R.string.contractor).toLowerCase() : getString(R.string.consumer).toLowerCase());
        RequestBody fileType = RequestBody.create(MediaType.parse("text/plain"), "image");
        DataManager.getInstance().uploadImage(activity, type, fileType, image, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                hideProgressBar();
                createAccount(response.toString());
            }
            @Override
            public void onError(Object error) {
                hideProgressBar();
                Utils.showError(SignUpActivity.this, constraintRoot, error);
            }
        });
    }

    private MultipartBody.Part prepareFilePart(String imagePath) {
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("file[0]", file.getName(), requestFile);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}