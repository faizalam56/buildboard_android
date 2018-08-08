package com.buildboard.modules.home.modules.profile.consumer;

import android.Manifest;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.profile.consumer.models.ProfileData;
import com.buildboard.modules.signup.models.contractortype.ContractorTypeDetail;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerData;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerRequest;
import com.buildboard.permissions.PermissionHelper;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.Utils;
import com.buildboard.view.SnackBarFactory;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.buildboard.utils.Utils.getImageUri;
import static com.buildboard.utils.Utils.selectImage;
import static com.buildboard.utils.Utils.showProgressColor;

public class EditProfileActivity extends AppCompatActivity implements AppConstant {

    private final String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private final int PICK_IMAGE_CAMERA = 2001;
    private final int PICK_IMAGE_GALLERY = 2002;
    private LatLng mAddressLatLng;
    private String mProvider;
    private String mProviderId;
    private String mEmail;
    private String mFirstName;
    private String mLastName;
    private Uri mSelectedImage;
    private ProfileData mProfileData;
    private String mResponsImageUrl;
    private ContractorTypeDetail contractorTypeDetail;
    private int maxClicks = 3, mCurrentNumber = 0;
    private String mContactMode = PHONE;
    private Bitmap mBitmap;
    public UpdateProfileListener mUpdateProfileListener;

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
    @BindView(R.id.text_address)
    BuildBoardTextView textAddress;
    @BindView(R.id.text_phone)
    BuildBoardTextView textPhone;
    @BindView(R.id.textAddAnotherAddress)
    BuildBoardTextView textAddAnotherAddress;
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
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

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
    @BindString(R.string.edit_profile)
    String stringEditProfile;
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
    @BindArray(R.array.user_type_array)
    String[] arrayUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        getIntentData();
        title.setText(stringEditProfile);
        getUserProfileData();
        setAsteriskToText();
        showProgressColor(this, progressBar);

        mUpdateProfileListener = ProfileFragment.newInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionHelper permission = new PermissionHelper(this);
            if (!permission.checkPermission(permissions))
                requestPermissions(permissions, REQUEST_PERMISSION_CODE);
        }

        radioGroupContactMode.setOnCheckedChangeListener(checkedChangeListener);
    }

    private void setAsteriskToText() {
        textFirstName.setText(Utils.setStarToLabel(getString(R.string.first_name)));
        textLastName.setText(Utils.setStarToLabel(getString(R.string.last_name)));
        textEmail.setText(Utils.setStarToLabel(getString(R.string.email)));
        textAddress.setText(Utils.setStarToLabel(getString(R.string.address)));
        textPhone.setText(Utils.setStarToLabel(getString(R.string.phone_no)));
        textContactMode.setText(Utils.setStarToLabel(getString(R.string.preferred_contact_mode)));
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
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
        }

        editEmail.setFocusable(mProviderId != null ? false : true);
        editEmail.setFocusableInTouchMode(mProviderId != null ? false : true);
        editEmail.setClickable(mProviderId != null ? false : true);
        editEmail.setCursorVisible(mProviderId != null ? false : true);
    }

    @OnClick(R.id.button_next)
    void nextButtonTapped() {
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String address = editAddress.getText().toString();
        String phoneNo = editPhoneNo.getText().toString();

        if (ConnectionDetector.isNetworkConnected(this)) {
            if (validateFields(firstName, lastName, address, phoneNo)) {
                signUpMethod(firstName, lastName, address, phoneNo, mContactMode, mResponsImageUrl);
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

    private void signUpMethod(String firstName, String lastName, String address, String phoneNo, String contactMode, String imageUrl) {
        createConsumer(firstName, lastName, address, phoneNo, contactMode, imageUrl);
    }

    private void createConsumer(String firstName, String lastName, String address, String phoneNo, String contactMode, String imageUrl) {
        CreateConsumerRequest consumerRequest = getConsumerDetails(firstName, lastName, address, phoneNo, contactMode, imageUrl);
        if (consumerRequest.getLatitude() == null || consumerRequest.getLongitude() == null) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringEnterValidAddress);
            return;
        }

        showProgressBar();
        DataManager.getInstance().updateConsumer(EditProfileActivity.this, consumerRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                hideProgressBar();
                if (response == null) return;

                CreateConsumerData createConsumerData = (CreateConsumerData) response;
                if (createConsumerData.getMessage() != null) {
                    Toast.makeText(EditProfileActivity.this, createConsumerData.getMessage(), Toast.LENGTH_LONG).show();
                    mUpdateProfileListener.updateProfile();
                    startActivity(new Intent(EditProfileActivity.this, ProfileSettingsActivity.class));
                }
            }

            @Override
            public void onError(Object error) {
                hideProgressBar();
                Utils.showError(EditProfileActivity.this, constraintRoot, error);
            }
        });
    }

    private CreateConsumerRequest getConsumerDetails(String firstName, String lastName, String address, String phoneNo, String contactMode, String imageUrl) {
        CreateConsumerRequest consumerRequest = new CreateConsumerRequest();
        consumerRequest.setFirstName(firstName);
        consumerRequest.setLastName(lastName);
        consumerRequest.setAddress(address);
        consumerRequest.setPhoneNo(phoneNo);
        consumerRequest.setContactMode(contactMode);

        if (!TextUtils.isEmpty(imageUrl))
            consumerRequest.setImage(imageUrl);

        if (mAddressLatLng != null) {
            consumerRequest.setLatitude(String.valueOf(mAddressLatLng.latitude));
            consumerRequest.setLongitude(String.valueOf(mAddressLatLng.longitude));
        }

        if (!TextUtils.isEmpty(mProvider) && !TextUtils.isEmpty(mProviderId)) {
            consumerRequest.setProvider(mProvider);
            consumerRequest.setProviderId(mProviderId);
        }

        return consumerRequest;
    }

    private boolean validateFields(String firstName, String lastName, String address, String phoneNo) {

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

        if (TextUtils.isEmpty(address)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorAddress).show();
            return false;
        }

        if (TextUtils.isEmpty(phoneNo)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorPhoneNumber).show();
            return false;
        } else if (phoneNo.length() < 9) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorValidPhoneNumber).show();
            return false;
        }

        return true;
    }

    RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            mContactMode = radioPhone.isSelected() ? PHONE : EMAIL;
        }
    };

    private void createAccount(String imageUrl) {
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String address = editAddress.getText().toString();
        String phoneNo = editPhoneNo.getText().toString();

        if (validateFields(firstName, lastName, address, phoneNo)) {
            signUpMethod(firstName, lastName, address, phoneNo, mContactMode, imageUrl);
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
                    mSelectedImage = data.getData();
                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mSelectedImage);
                        imageProfile.setImageBitmap(mBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case PICK_IMAGE_CAMERA:
                    try {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            mBitmap = (Bitmap) extras.get("data");
                            mSelectedImage = getImageUri(this, mBitmap);
                            imageProfile.setImageBitmap(mBitmap);
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
        mAddressLatLng = place.getLatLng();
    }

    private void showAddressDialog(Place place) {
        if (place != null) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_custom_places, null);
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
                        SnackBarFactory.createSnackBar(EditProfileActivity.this, constraintRoot, stringCheckLocation);
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

    public void uploadImage(Activity activity, MultipartBody.Part image) {
        showProgressBar();
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), AppPreference.getAppPreference(this).getBoolean(IS_CONTRACTOR) ? getString(R.string.contractor).toLowerCase() : getString(R.string.consumer).toLowerCase());
        RequestBody fileType = RequestBody.create(MediaType.parse("text/plain"), "image");
        DataManager.getInstance().uploadImage(activity, type, fileType, image, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                hideProgressBar();
                mResponsImageUrl = response.toString();
            }

            @Override
            public void onError(Object error) {
                hideProgressBar();
                Utils.showError(EditProfileActivity.this, constraintRoot, error);
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

    private void getUserProfileData() {
        showProgressBar();
        DataManager.getInstance().getProfile(EditProfileActivity.this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                hideProgressBar();
                mProfileData = (ProfileData) response;
                setProfileData(mProfileData);
            }

            @Override
            public void onError(Object error) {
                hideProgressBar();
            }
        });
    }

    private void setProfileData(ProfileData profileData) {
        if (profileData != null) {
            Picasso.get().load(profileData.getImage()).resize(80, 80).error(R.drawable.upload_profile_image).into(imageProfile);
            mAddressLatLng = new LatLng(profileData.getLatitude(), profileData.getLongitude());
            editFirstName.setText(profileData.getFirstName());
            editLastName.setText(profileData.getLastName());
            editEmail.setText(profileData.getEmail());
            editAddress.setText(profileData.getAddress());
            editPhoneNo.setText(profileData.getPhoneNo());
            if (profileData.getContactMode().equals("phone")) {
                radioPhone.setChecked(true);
            } else {
                radioEmail.setChecked(true);
            }
        }
    }

    //TODO WORK ON ADD NEW MULTIPLE ADDRESS
    @OnClick(R.id.textAddAnotherAddress)
    public void addNewAddressBox() {
        final LinearLayout linearLayoutForm = this.findViewById(R.id.linearLayoutForm);
        if (mCurrentNumber == maxClicks) {
            textAddAnotherAddress.setVisibility(View.GONE);
        } else {
            textAddAnotherAddress.setVisibility(View.VISIBLE);
            mCurrentNumber = mCurrentNumber + 1;
            final LinearLayout newView = (LinearLayout) this.getLayoutInflater().inflate(R.layout.dialog_custom_add_address_layout, null);
            newView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            BuildBoardTextView text_address = newView.findViewById(R.id.text_address);
            BuildBoardTextView edit_address = newView.findViewById(R.id.edit_address);
            text_address.setText(String.format(Locale.getDefault(), "%s %d", getString(R.string.address), mCurrentNumber));
            edit_address.setHint(getString(R.string.address) + " " + mCurrentNumber);
            ImageView btnRemove = newView.findViewById(R.id.btnRemove);
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentNumber--;
                    if (textAddAnotherAddress.getVisibility() == View.GONE || mCurrentNumber > 3) {
                        textAddAnotherAddress.setVisibility(View.VISIBLE);
                    }
                    linearLayoutForm.removeView(newView);
                }
            });
            linearLayoutForm.addView(newView);
        }
    }

    public interface UpdateProfileListener {
        void updateProfile();
    }
}