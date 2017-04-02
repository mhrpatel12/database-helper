package com.hdfc.claims.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hdfc.claims.Utilities.Globals;
import com.hdfc.claims.Utilities.LogUtils;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Globals.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void execute(String Statment) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL(Statment);
            LogUtils.LOGV(" execute SQL : ", Statment);
        } catch (Exception e) {
            LogUtils.LOGE("DataBaseHelper - ExecuteQuery", e.getMessage());
            LogUtils.LOGV("SQL : ", Statment);
        } finally {
            db.close();
            db = null;
        }
    }

    public void executeSQL(String Statment) {
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            db.execSQL(Statment);
            LogUtils.LOGV(" execute SQL : ", Statment);
        } catch (Exception e) {
            LogUtils.LOGE("DataBaseHelper - ExecuteQuery", e.getMessage());
            LogUtils.LOGV("SQL : ", Statment);
        } finally {
            db.close();
            db = null;
        }
    }

    public int insert(String Statment) {
        int retVal = 0;
        Cursor cur = null;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            LogUtils.LOGV("SQL : ", Statment);
            db.execSQL(Statment);

            cur = db.rawQuery("SELECT last_insert_rowid()", null);
            cur.moveToPosition(0);

            retVal = cur.getInt(0);

        } catch (Exception e) {
            LogUtils.LOGE("DataBaseHelper - ExecuteQuery", e.getMessage());
            LogUtils.LOGV("SQL : ", Statment);
        } finally {
            if (cur != null)
                cur.close();

            cur = null;

            db.close();
            db = null;
        }

        return retVal;
    }

    public long insert(String tableName, ContentValues values) {
        long insertedID = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            insertedID = db.insert(tableName, null, values);
            LogUtils.LOGV("SQL : ", "------- insertedID : " + insertedID);
        } catch (Exception e) {
            LogUtils.LOGE("DataBaseHelper - ExecuteQuery", e.getMessage());
        } finally {
            db.close();
            db = null;
        }
        return insertedID;
    }


    public void update(String tableName, ContentValues values, String condition, String[] args) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
        try {
            //db.beginTransaction();
            id = db.update(tableName, values, condition, args);
            LogUtils.LOGV(" update SQL : ", condition + "ROW AFFECTED" + id);
            //db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.LOGE("DataBaseHelper - update", e.getMessage());
            LogUtils.LOGV("SQL : update() ", "tableName ::" + tableName + " condition::" + condition);
        } finally {
            db.close();
        }
        db = null;
    }

    public Cursor query(String Statement) {
        Cursor cur = null;
        SQLiteDatabase db;
        if (Statement.contains("select")) {
            db = this.getReadableDatabase();
        } else {
            db = this.getWritableDatabase();
        }


        try {
            cur = db.rawQuery(Statement, null);
            cur.moveToPosition(-1);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.LOGE("DataBaseHelper - ExecuteQuery", e.getMessage());
            LogUtils.LOGV("SQL : ", Statement);
        } finally {
            db.close();
        }
        db = null;

        return cur;
    }

    public boolean checkExists(String tableName, String column, String _value) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isExist = false;
        try {
            isExist = DatabaseUtils.queryNumEntries(db, tableName, column + "=?", new String[]{_value}) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
        return isExist;
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        Cursor cur = null;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            cur = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            cur.moveToPosition(-1);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.LOGE("DataBaseHelper - ExecuteQuery", e.getMessage());

        } finally {
            db.close();
            db = null;
        }

        return cur;
    }

    public static String getDBStr(String str) {

        str = str != null ? str.replaceAll("\'", "\'\'") : null;
        return str;

    }

    public void upgrade(int level) {
        switch (level) {
            case 0:
                doUpdate0();
            case 1:
                //doUpdate1();
            case 2:
                // doUpdate2();
            case 3:
                // doUpdate3();

        }
    }


    //TODO: Master tables
    public static final String LAST_SYNC_INFO_TABLE = "LAST_SYNC_INFO_TABLE";
    public static final String DASHBOARD_CLAIM_TABLE = "DASHBOARD_CLAIM_TABLE";
    public static final String FLAG_DETAIL_TABLE = "FLAG_DETAIL_TABLE";
    public static final String CLAIM_SYNC_DATE_TABLE = "CLAIM_SYNC_DATE_TABLE";
    public static final String INSURED_DETAIL_TABLE = "INSURED_DETAIL_TABLE";
    public static final String DL_N_RC_DETAIL_TABLE = "DL_N_RC_DETAIL_TABLE";
    public static final String ISSUANCE_RTO_MASTER_TABLE = "ISSUANCE_RTO_MASTER_TABLE";
    public static final String DRIVER_TYPE_MASTER_TABLE = "DRIVER_TYPE_MASTER_TABLE";
    public static final String WORKSKHOP_MASTER_TABLE = "WORKSKHOP_MASTER_TABLE";
    public static final String BREAKIN_INFO_TABLE = "BREAKIN_INFO_TABLE";
    public static final String CIMA_REMARKS_TABLE = "CIMA_REMARKS_TABLE";
    public static final String BREAKIN_IMAGES_TABLE = "BREAKIN_IMAGES_TABLE";
    public static final String PAST_CLAIM_DETAILS_TABLE = "PAST_CLAIM_DETAILS_TABLE";
    public static final String PAST_CLAIM_IMAGES_TABLE = "PAST_CLAIM_IMAGES_TABLE";
    public static final String PAST_CLAIM_IMAGES_BASE64_TABLE = "PAST_CLAIM_IMAGES_BASE64_TABLE";
    public static final String PAST_CLAIM_IMAGES_HAS_DATA_TABLE = "PAST_CLAIM_IMAGES_HAS_DATA_TABLE";
    public static final String SURVEY_DETAILS_TABLE = "SURVEY_DETAILS_TABLE";
    public static final String LOSS_TYPE_MASTER_TABLE = "LOSS_TYPE_MASTER_TABLE";
    public static final String VEHICLE_COLOR_MASTER_TABLE = "VEHICLE_COLOR_MASTER_TABLE";
    public static final String VEHICLE_COLOR_TYPE_MASTER_TABLE = "VEHICLE_COLOR_TYPE_MASTER_TABLE";
    public static final String POINT_OF_IMPACT_HAS_DATA_TABLE = "POINT_OF_IMPACT_HAS_DATA_TABLE";
    public static final String POINT_OF_IMPACT_DATA_TABLE = "POINT_OF_IMPACT_DATA_TABLE";
    public static final String CP_LOSS_TABLE = "CP_LOSS_TABLE";
    public static final String CLAIMANT_DETAILS_TABLE = "CLAIMANT_DETAILS_TABLE";
    public static final String NEFT_TABLE = "NEFT_TABLE";
    public static final String INSURED_EXISTING_REJECTED_TABLE = "INSURED_EXISTING_REJECTED_TABLE";
    public static final String BENEFICIARY_EXISTING_REJECTED_TABLE = "BENEFICIARY_EXISTING_REJECTED_TABLE";
    public static final String CP_EXPENSE_TABLE = "CP_EXPENSE_TABLE";
    public static final String RESOURCE_MASTER_TABLE = "RESOURCE_MASTER_TABLE";
    public static final String WORKSHOP_SELECTION_TABLE = "WORKSHOP_SELECTION_TABLE";
    public static final String WORKSHOP_SELECTION_TWO_TABLE = "WORKSHOP_SELECTION_TWO_TABLE";
    public static final String PRE_APPROVAL_TABLE = "PRE_APPROVAL_TABLE";
    public static final String CLAIM_STAGE_MASTER_TABLE = "CLAIM_STAGE_MASTER_TABLE";
    public static final String CLAIM_STAGE_TABLE = "CLAIM_STAGE_TABLE";
    public static final String PREVIOUS_REMARKS_TABLE = "PREVIOUS_REMARKS_TABLE";
    public static final String LICENSE_TYPE_MASTER_TABLE = "LICENSE_TYPE_MASTER_TABLE";
    public static final String POLICY_INFO_TABLE = "POLICY_INFO_TABLE";
    public static final String CLAIM_DETAILS_TABLE = "CLAIM_DETAILS_TABLE";
    public static final String COMPUTATION_DATA_ENTRY_COMMON_TABLE = "COMPUTATION_DATA_ENTRY_TABLE";
    public static final String ADDON_DETAILS_TABLE = "ADDON_DETAILS_TABLE";
    public static final String DEPRECIATION_MASTER_TABLE = "DEPRECIATION_MASTER_TABLE";
    public static final String DISCOUNT_MASTER_TABLE = "DISCOUNT_MASTER_TABLE";
    public static final String CITY_GROUP_MASTER_TABLE = "CITY_GROUP_MASTER_TABLE";
    public static final String CLAIM_PROCESSING_TABLE = "CLAIM_PROCESSING_TABLE";
    public static final String COMPUTATION_DATAENTRY_WORKSHOPONE_TABLE = "COMPUTATION_DATAENTRY_WORKSHOPONE_TABLE";
    public static final String COMPUTATION_DATAENTRY_WORKSHOPTWO_TABLE = "COMPUTATION_DATAENTRY_WORKSHOPTWO_TABLE";

    public static final String PARTS_WORKSHOP_ONE_TABLE = "PARTS_WORKSHOP_ONE_TABLE";
    public static final String PARTS_WORKSHOP_TWO_TABLE = "PARTS_WORKSHOP_TWO_TABLE";
    public static final String SMS_LAST_SYNC_TABLE = "SMS_LAST_SYNC_TABLE";
    public static final String SMS_DETAILS_TABLE = "SMS_DETAILS_TABLE";
    public static final String DOCUMENT_MASTER_TABLE = "DOCUMENT_MASTER_TABLE";

    //SMS_LAST_SYNC_TABLE
    public static final String PRIMARY_KEY_ID = "_id";
    public static final String SMS_CLAIM_NO = "sms_claim_number";
    public static final String SMS_SYNC_DATETIME = "sms_last_sync";
    //SMS_DETAILS_TABLE
    public static final String SMS_MOBILE_NO = "sms_mobile";
    public static final String SMS_SENDER = "sms_sender";
    public static final String SMS_BODY = "sms_body";
    public static final String SMS_DATE = "sms_date";
    //DOCUMENT_MASTER_TABLE
    public static final String DOC_CAT = "doc_category";

    //LAST_SYNC_INFO TABLE
    public static final String MASTER_NAME = "master_name";
    public static final String SYNC_DATE = "sync_date";
    public static final String SYNC_DATE_TIME = "sync_date_time";
    public static final String LAST_SYNC_DATE_TIME = "last_sync_date_time";


    //dashboard
    public static final String MASTER_CLAIM_NUMBER = "master_claim_number";
    public static final String VEHICLE_REGISTRATION_NO = "vehicle_registration_no";
    public static final String VEHICLE_MODEL = "vehicle_model";
    public static final String INSURED_NAME = "insured_name";
    public static final String TAT = "tat";
    public static final String CLAIM_STAGE = "claim_stage";
    public static final String SERIAL_NO = "serial_no";
    public static final String START_TIME = "start_time";
    public static final String CLAIM_TYPE = "claim_type";
    public static final String INSURED_CITY = "insured_city";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String INBOX_STATUS = "inbox_status";
    public static final String CLAIM_NUMBER = "claim_number";
    public static final String COLOR_CODING = "color_coding";
    public static final String CLAIM_STATUS = "claim_status";
    public static final String POLICY_NUMBER = "policy_number";
    public static final String SURVEYOR_CODE = "surveyor_code";
    public static final String COMPLETION_PERCENTAGE = "completion_percentage";

    //Flags
    public static final String FLAG_CODE = "flag_code";
    public static final String FLAG_STATUS = "flag_status";
    public static final String FLAG_DESCRIPTION = "flag_description";

    //insured
    public static final String INSURED_ADDRESS = "insured_address";
    public static final String INSURED_PIN_CODE = "insured_pin_code";
    public static final String INSURED_MOBILE_NUMBER = "insured_mobile_number";
    public static final String INSURED_EMAIL = "insured_email";
    public static final String CUSTOMER_ID = "customer_id";


    //CIMA REMARKS // CLAIM HISTORY
    public static final String CIMA_REMARKS = "cima_remarks";

    /*DL N RC DETAILS*/
    //DL
    public static final String HAS_LICENSE = "has_license";
    public static final String DRIVER_NAME = "driver_name";
    public static final String DRIVER_DOB = "driver_dob";
    public static final String DRIVER_GENDER = "driver_gender";
    public static final String ISSUANCE_DATE = "issuance_date";
    public static final String EXPIRY_DATE = "expiry_date";
    public static final String LICENSE_NUMBER = "license_number";
    public static final String ISSUANCE_RTO = "issuance_rto";
    //RC
    public static final String DATE_OF_VEHICLE_REGISTRATION = "date_of_vehicle_registration";
    public static final String TRANSFER_DATE = "transfer_date";
    public static final String VEHICLE_MAKE = "vehicle_make";
    public static final String REFINED_VEHICLE_MAKE = "refined_vehicle_make";
    public static final String REFINED_VEHICLE_MODEL = "refined_vehicle_model";
    public static final String ENGINE_NUMBER = "engine_number";
    public static final String CHASSIS_NUMBER = "chassis_number";
    public static final String RTO_NAME = "rto_name";
    public static final String ODOMETER_READING = "odometer_reading";
    //FIR
    public static final String HAS_FIR = "has_fir";
    public static final String FIR_DATE = "fir_date";
    public static final String POLICE_STATION = "police_station";
    public static final String FIR_UNDER_SECTION = "fir_under_section";

    //ISSUANCE RTO MASTER
    //public static final String ISSUANCE_RTO = "issuance_rto";

    //DRIVER TYPE MASTER
    public static final String DRIVER_TYPE_ID = "driver_type_id";
    public static final String DRIVER_TYPE = "driver_type";

    //Workshop Master
    public static final String WORKSHOP_ID = "workshop_id";
    public static final String WORKSHOP_NAME = "workshop_name";
    public static final String WORKSHOP_MOBILE = "workshop_mobile";
    public static final String WORKSHOP_ADDRESS = "workshop_address";
    public static final String WORKSHOP_PAYMENT_MODE = "workshop_payment_mode";
    public static final String WORKSHOP_CITY = "workshop_city";
    public static final String WORKSHOP_TYPE_CD = "workshop_type_cd";

    //CLAIM DETAILS
    public static final String LOSS_DATE = "loss_date";
    public static final String LOSS_TIME = "loss_time";
    public static final String DATE_OF_REGISTRATION = "date_of_registration";
    public static final String DATE_OF_NOTIFICATION = "date_of_notification";
    public static final String POLICY_START_DATE = "policy_start_date";
    public static final String POLICY_END_DATE = "policy_end_date";

    //BREAKIN INFO
    public static final String BREAKIN_NUMBER = "breakin_number";
    public static final String BREAKIN_DECISION = "breakin_decision";
    public static final String BREAKIN_APPROVAL_DATE = "breakin_approval_date";
    public static final String BREAKIN_REMARKS = "breakin_remarks";

    public static final String BREAKIN_ID = "breakin_id";
    public static final String BREAKIN_IMAGE_NAME = "breakin_image_name";
    public static final String BREAKIN_IMAGE_BASE64 = "breakin_image_base64";

    //PAST CLAIM DETAILS
    public static final String PAST_CLAIM_DETAILS_JSON_STRING = "past_claim_details_json_string";
    public static final String CLAIM_REPUDIATED = "claim_repudiated";
    public static final String LOSS_PAID_AMOUNT = "loss_paid_amount";
    public static final String LOSS_PAYEE_NAME = "loss_payee_name";
    public static final String DOC_ID = "doc_id";
    public static final String DOC_NAME = "doc_name";
    public static final String BASE_64 = "base_64";

    //SURVEY DETAILS
    public static final String SURVEY_START_DATE = "survey_start_date";
    public static final String SURVEY_TIME = "survey_time";
    public static final String SURVEY_ESTIMATE_DATE = "survey_estimate_date";
    public static final String SURVEY_REINSPECTION_DATE = "survey_reinspection_date";
    public static final String VEHICLE_REPORT_DATE = "vehicle_report_date";
    public static final String VEHICLE_REPORT_TIME = "vehicle_report_time";
    public static final String SURVEY_LAST_DOCUMENT_READ_DATE = "survey_last_document_read_date";
    public static final String SURVEY_COMPUTED_AMOUNT = "survey_computed_amount";
    public static final String SURVEY_ESTIMATED_AMOUNT = "survey_estimated_amount";
    public static final String SURVEY_REMARKS = "survey_remarks";
    public static final String SURVEY_SALVAGE_INVOLVED = "survey_salvage_involved";
    public static final String SURVEY_TOTAL_LOSS = "survey_total_loss";

    //LOSS TYPE MASTER
    public static final String SEQUENCE_NO = "sequence_no";
    public static final String LOSS_REASON = "loss_reason";

    //VEHICLE VEHICLE_COLOR MASTER
    public static final String VEHICLE_COLOR_CODE = "color_code";
    public static final String VEHICLE_COLOR = "color";

    //VEHICLE VEHICLE_COLOR TYPE MASTER
    public static final String VEHICLE_COLOR_TYPE_ID = "color_type_id";
    public static final String VEHICLE_COLOR_TYPE = "color_type";

    //POINT OF IMPACT
    public static final String PART_CODE_POI = "part_code";
    public static final String PART_NAME_POI = "part_name";
    public static final String INTENSITY_POI = "intensity";

    public static final String MASTER_CLAIM_NUMBER_PAYEE = "master_claim_number_payee";
    //CP LOSS
    public static final String PAYEE_TYPE = "payee_type";
    public static final String PAYEE_NAME = "payee_name";
    public static final String PAYEE_ADDRESS = "payee_address";
    public static final String SERVICE_TAX = "service_tax";
    public static final String TDS = "tds";
    public static final String NET_AMOUNT = "net_amount";
    public static final String PAY_TYPE = "pay_type";
    public static final String PAYMENT_MODE = "payment_mode";

    public static final String PAYEE_TYPE_ID = "payee_type_id";
    public static final String PAYEE_ID = "payee_id";
    public static final String FLAG = "flag";
    public static final String ON_ACCOUNT_FLAG = "on_account_flag";
    public static final String CHEQUE_ISSUED_FLAG = "cheque_issued_flag";
    public static final String PAYMENT_SERIAL_NO = "payment_serial_no";
    public static final String CLMNT_NAME = "clmnt_name";

    //CLAIMANT DETAILS
    public static final String CLAIMANT_CODE = "claimant_code";
    public static final String CLAIMANT_NAME = "claimant_name";
    public static final String CLAIMANT_ADDRESS = "claimant_address";

    //CP EXPENSE
    public static final String PROFESSIONAL_FEES = "professional_fees";
    public static final String EXPENSE = "expense";
    public static final String TOTAL_BILL = "total_bill";
    public static final String CHEQUE_AMOUNT = "cheque_amount";
    public static final String EXPENSE_BILL_DATE = "expense_bill_date";
    public static final String SERVICE_TAX_APPLICABILITY = "service_tax_applicability";

    public static final String VENDOR_TYPE_ID = "vendor_type_id";
    public static final String VENDOR_TYPE_DESCRIPTION = "vendor_type_description";
    public static final String VENDOR_ID = "vendor_id";
    public static final String VENDOR_NAME = "vendor_name";
    public static final String VENDOR_ADDRESS = "vendor_address";
    public static final String NUMERIC_PAYMENT_SERIAL_NO = "numeric_payment_serial_no";

    //RESOURCE MASTER
    public static final String RESOURCE_ID = "resource_id";
    public static final String RESOURCE_NAME = "resource_name";
    public static final String RESOURCE_ADDRESS = "resource_address";
    public static final String RESOURCE_PAYMENT_MODE = "resource_payment_mode";

    //NEFT ENTRY
    public static final String IFSC_CODE = "ifsc_code";
    public static final String MOBILE_NUMBER = "mobile_number";
    public static final String ACCOUNT_NAME = "account_name";
    public static final String ACCOUNT_NUMBER = "account_number";
    public static final String BANK_NAME = "bank_name";
    public static final String BRANCH_NAME = "branch_name";
    public static final String EMAIL_ID = "email_id";

    //INSURED EXISTING REJECTED
    public static final String IS_REJECTED = "is_rejected";
    public static final String ACCOUNT_ID = "account_id";
    public static final String CUSTOMER_NAME = "customer_name";

    //BENEFICIARY EXISTING REJECTED
    public static final String BENEFICIARY_NAME = "beneficiary_name";
    public static final String BENEFICIARY_ADDRESS = "beneficiary_address";
    public static final String ISD_CODE = "isd_code";
    public static final String STD_CODE = "std_code";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String MOBILE_ISD = "mobile_isd";
    public static final String MICR_CODE = "micr_code";
    public static final String ACTIVE = "active";
    public static final String ACCOUNT_STATUS = "account_status";

    //WORKSHOP_SELECTION
    public static final String HAS_DATA = "has_data";
    public static final String SURVEY_LOCATION = "survey_location";
    public static final String HAS_WORKSHOP_TWO = "has_worshop_two";
    public static final String INVOICE_NUMBER = "invoice_number";
    public static final String INVOICE_DATE = "invoice_date";
    public static final String INVOICE_AMOUNT = "invoice_amount";
    public static final String BILL_SUBMIT_DATE = "bill_submit_date";
    public static final String REPAIR_DETAILS = "repair_details";

    //PRE APPROVAL
    public static final String PRE_APPROVAL_json = "pre_approval_json";

    //CLAIM STAGE
    public static final String CLAIM_STAGE_REMARK = "claim_stage_remark";

    //PREVIOUS REMARKS
    public static final String DATE = "date";
    public static final String SURVEYOR = "surveyor";

    //LICENSE TYPE MASTER
    public static final String LICENSE_TYPE_ID = "license_type_id";
    public static final String LICENSE_DISPLAY_NAME = "license_display_name";
    public static final String LICENSE_TYPE = "license_type";
    public static final String PRODUCT = "product";

    //DEPRECIATION MASTER
    public static final String DEPRECIATION = "depreciation";
    public static final String AGE_FROM = "age_from";
    public static final String AGE_TO = "age_to";

    //DISCOUNT MASTER
    public static final String MAKE = "make";
    public static final String DISCOUNT_TYPE = "discount_type";
    public static final String SERIAL_NUMBER = "serial_number";
    public static final String FROM = "_from";
    public static final String TO = "_to";
    public static final String DISCOUNT_PERCENTAGE = "discount_percentage";

    //CITY GROUP MASTER
    public static final String MANUFACTURER = "manufacturer";
    public static final String CITY_NAME = "city_name";
    public static final String GROUP = "_group";

    //COMPUTATION DATA ENTRY
    public static final String COMPULSARY_EXCESS = "compulsary_excess";
    public static final String VOLUNTARY_EXCESS = "voluntary_excess";
    public static final String SALVAGE_AMOUNT = "salvage_amount";
    public static final String DEPRECIATION_PERCENTAGE = "depreciation_percentage";

    //CLAIM PROCESSING
    public static final String INSURED_DETAILS = "insured_details";
    public static final String CLAIM_HISTORY = "claim_history";
    public static final String SURVEY_DETAILS = "survey_details";
    public static final String POINT_OF_IMPACT = "point_of_impact";
    public static final String WORKSHOP_DETAILS = "workshop_details";
    public static final String DL_N_RC_DETAILS = "dl_n_rc_details";
    public static final String UPLOAD_DOCUMENTS = "documents_upload";
    public static final String COMPUTATION_DATA_ENTRY = "computation_data_entry";
    public static final String CP_LOSS = "cp_loss";
    public static final String CP_EXPENSE = "cp_expense";

    //POLICY INFO

    public static final String POLICY_INFO_JSON_STRING = "policy_info_json_string";

    //ADDON DETAILS
    public static final String SI_VALUE = "si_value";
    public static final String DEDUCTIBLE = "deductible";
    public static final String BENEFIT = "benefit";
    public static final String COMPUTED_AMOUNT = "computed_amount";
    public static final String AMOUNT_PER_DAY = "amount_per_day";

    public static final String IDV_VALUE = "idv_value";
    public static final String INVOICE_VALUE = "invoice_value";
    public static final String FIRST_TIME_REGISTRATION = "first_time_registration";
    public static final String TOTAL_VALUE = "total_value";
    public static final String TOTAL_PAYABLE_UNDER_RTI = "total_payable_under_rti";

    public static final String REPAIR_START_DATE = "repair_start_date";
    public static final String REPAIR_END_DATE = "repair_end_date";
    public static final String EMI_TO_BE_PAID = "emi_to_be_paid";
    public static final String EMI_PROTECTOR = "emi_protector";
    public static final String TOTAL_PAYABLE = "total_payable";

    public static final String HIGHER_PROTECTION_REMOVAL_COST = "higher_protection_removal_cost";

    //Computation data entry workshop fields
    public static final String PARTS_TAX_RATE = "parts_tax_rate";
    public static final String PARTS_OTHER_TAX = "parts_other_tax";
    public static final String LABOUR_OTHER_TAX = "labour_other_tax";
    public static final String PAINT_MATERIAL_BREAKDOWN = "paint_material_breakdown";
    public static final String PAINT_TAX_LOADING = "paint_tax_loading";


    //SELECTED PARTS
    public static final String SELECTED_PARTS_WORKSHOP_ONE_TABLE = "SELECTED_PARTS_WORKSHOP_ONE_TABLE";
    public static final String SELECTED_PARTS_WORKSHOP_TWO_TABLE = "SELECTED_PARTS_WORKSHOP_TWO_TABLE";

    //CUSTOM PARTS
    public static final String CUSTOM_PARTS_COUNT_ONE_TABLE = "CUSTOM_PARTS_COUNT_ONE_TABLE";
    public static final String CUSTOM_PARTS_COUNT_TWO_TABLE = "CUSTOM_PARTS_COUNT_TWO_TABLE";

    public static final String PART_NAME_MASTER_TABLE = "PART_NAME_MASTER_TABLE";
    public static final String PART_PRICE_MASTER_TABLE = "PART_PRICE_MASTER_TABLE";
    public static final String PAINT_PRICE_MASTER_TABLE = "PAINT_PRICE_MASTER_TABLE";

    //Part price Master

    public static final String CAR_MAKE = "MAKE";
    public static final String CAR_MODEL = "MODEL";
    public static final String YEAR_FROM = "YEARFROM";
    public static final String YEAR_TO = "YEARTO";
    public static final String PART_PRICE = "PART";
    public static final String REMOVE_REFIT_PRICE = "REMOVEREFIT";

    //Paint price master

    //PartMaster.db //Generated Using API
    public static final String PAINT_FULL_SOLID_GROUP1 = "PAINTFULLSOLID_GROUP1";
    public static final String PAINT_FULL_METALLIC_GROUP1 = "PAINTFULLMETALLIC_GROUP1";
    public static final String PAINT_FULL_PEARL_GROUP1 = "PAINTFULLPEARL_GROUP1";
    public static final String PAINT_FULL_MATTE_GROUP1 = "PAINTFULLMATTE_GROUP1";
    public static final String PAINT_PARTIAL_SOLID_GROUP1 = "PAINTPARTIALSOLID_GROUP1";
    public static final String PAINT_PARTIAL_METALLIC_GROUP1 = "PAINTPARTIALMETALLIC_GROUP1";
    public static final String PAINT_PARTIAL_PEARL_GROUP1 = "PAINTPARTIALPEARL_GROUP1";
    public static final String PAINT_PARTIAL_MATTE_GROUP1 = "PAINTPARTIALMATTE_GROUP1";
    public static final String PAINT_FULL_SOLID_GROUP2 = "PAINTFULLSOLID_GROUP2";
    public static final String PAINT_FULL_METALLIC_GROUP2 = "PAINTFULLMETALLIC_GROUP2";
    public static final String PAINT_FULL_PEARL_GROUP2 = "PAINTFULLPEARL_GROUP2";
    public static final String PAINT_FULL_MATTE_GROUP2 = "PAINTFULLMATTE_GROUP2";
    public static final String PAINT_PARTIAL_SOLID_GROUP2 = "PAINTPARTIALSOLID_GROUP2";
    public static final String PAINT_PARTIAL_METALLIC_GROUP2 = "PAINTPARTIALMETALLIC_GROUP2";
    public static final String PAINT_PARTIAL_PEARL_GROUP2 = "PAINTPARTIALPEARL_GROUP2";
    public static final String PAINT_PARTIAL_MATTE_GROUP2 = "PAINTPARTIALMATTE_GROUP2";
    public static final String PAINT_FULL_SOLID_GROUP3 = "PAINTFULLSOLID_GROUP3";
    public static final String PAINT_FULL_METALLIC_GROUP3 = "PAINTFULLMETALLIC_GROUP3";
    public static final String PAINT_FULL_PEARL_GROUP3 = "PAINTFULLPEARL_GROUP3";
    public static final String PAINT_FULL_MATTE_GROUP3 = "PAINTFULLMATTE_GROUP3";
    public static final String PAINT_PARTIAL_SOLID_GROUP3 = "PAINTPARTIALSOLID_GROUP3";
    public static final String PAINT_PARTIAL_METALLIC_GROUP3 = "PAINTPARTIALMETALLIC_GROUP3";
    public static final String PAINT_PARTIAL_PEARL_GROUP3 = "PAINTPARTIALPEARL_GROUP3";
    public static final String PAINT_PARTIAL_MATTE_GROUP3 = "PAINTPARTIALMATTE_GROUP3";
    public static final String PAINT_FULL_SOLID_GROUP4 = "PAINTFULLSOLID_GROUP4";
    public static final String PAINT_FULL_METALLIC_GROUP4 = "PAINTFULLMETALLIC_GROUP4";
    public static final String PAINT_FULL_PEARL_GROUP4 = "PAINTFULLPEARL_GROUP4";
    public static final String PAINT_FULL_MATTE_GROUP4 = "PAINTFULLMATTE_GROUP4";
    public static final String PAINT_PARTIAL_SOLID_GROUP4 = "PAINTPARTIALSOLID_GROUP4";
    public static final String PAINT_PARTIAL_METALLIC_GROUP4 = "PAINTPARTIALMETALLIC_GROUP4";
    public static final String PAINT_PARTIAL_PEARL_GROUP4 = "PAINTPARTIALPEARL_GROUP4";
    public static final String PAINT_PARTIAL_MATTE_GROUP4 = "PAINTPARTIALMATTE_GROUP4";


    //old PartMaster.db
/*    public static final String PAINT_FULL_SOLID_GROUP1 = "PAINTFULLSOLID-GROUP1";
    public static final String PAINT_FULL_METALLIC_GROUP1 = "PAINTFULLMETALLIC-GROUP1";
    public static final String PAINT_FULL_PEARL_GROUP1 = "PAINTFULLPEARL-GROUP1";
    public static final String PAINT_FULL_MATTE_GROUP1 = "PAINTFULLMATTE-GROUP1";
    public static final String PAINT_PARTIAL_SOLID_GROUP1 = "PAINTPARTIALSOLID-GROUP1";
    public static final String PAINT_PARTIAL_METALLIC_GROUP1 = "PAINTPARTIALMETALLIC-GROUP1";
    public static final String PAINT_PARTIAL_PEARL_GROUP1 = "PAINTPARTIALPEARL-GROUP1";
    public static final String PAINT_PARTIAL_MATTE_GROUP1 = "PAINTPARTIALMATTE-GROUP1";
    public static final String PAINT_FULL_SOLID_GROUP2 = "PAINTFULLSOLID-GROUP2";
    public static final String PAINT_FULL_METALLIC_GROUP2 = "PAINTFULLMETALLIC-GROUP2";
    public static final String PAINT_FULL_PEARL_GROUP2 = "PAINTFULLPEARL-GROUP2";
    public static final String PAINT_FULL_MATTE_GROUP2 = "PAINTFULLMATTE-GROUP2";
    public static final String PAINT_PARTIAL_SOLID_GROUP2 = "PAINTPARTIALSOLID-GROUP2";
    public static final String PAINT_PARTIAL_METALLIC_GROUP2 = "PAINTPARTIALMETALLIC-GROUP2";
    public static final String PAINT_PARTIAL_PEARL_GROUP2 = "PAINTPARTIALPEARL-GROUP2";
    public static final String PAINT_PARTIAL_MATTE_GROUP2 = "PAINTPARTIALMATTE-GROUP2";
    public static final String PAINT_FULL_SOLID_GROUP3 = "PAINTFULLSOLID-GROUP3";
    public static final String PAINT_FULL_METALLIC_GROUP3 = "PAINTFULLMETALLIC-GROUP3";
    public static final String PAINT_FULL_PEARL_GROUP3 = "PAINTFULLPEARL-GROUP3";
    public static final String PAINT_FULL_MATTE_GROUP3 = "PAINTFULLMATTE-GROUP3";
    public static final String PAINT_PARTIAL_SOLID_GROUP3 = "PAINTPARTIALSOLID-GROUP3";
    public static final String PAINT_PARTIAL_METALLIC_GROUP3 = "PAINTPARTIALMETALLIC-GROUP3";
    public static final String PAINT_PARTIAL_PEARL_GROUP3 = "PAINTPARTIALPEARL-GROUP3";
    public static final String PAINT_PARTIAL_MATTE_GROUP3 = "PAINTPARTIALMATTE-GROUP3";
    public static final String PAINT_FULL_SOLID_GROUP4 = "PAINTFULLSOLID-GROUP4";
    public static final String PAINT_FULL_METALLIC_GROUP4 = "PAINTFULLMETALLIC-GROUP4";
    public static final String PAINT_FULL_PEARL_GROUP4 = "PAINTFULLPEARL-GROUP4";
    public static final String PAINT_FULL_MATTE_GROUP4 = "PAINTFULLMATTE-GROUP4";
    public static final String PAINT_PARTIAL_SOLID_GROUP4 = "PAINTPARTIALSOLID-GROUP4";
    public static final String PAINT_PARTIAL_METALLIC_GROUP4 = "PAINTPARTIALMETALLIC-GROUP4";
    public static final String PAINT_PARTIAL_PEARL_GROUP4 = "PAINTPARTIALPEARL-GROUP4";
    public static final String PAINT_PARTIAL_MATTE_GROUP4 = "PAINTPARTIALMATTE-GROUP4";*/


    //Part Name Master

    public static final String PART_CODE = "PARTCODE";
    public static final String SUB_BUCKET = "SUBBUCKET";
    public static final String PART_NAME = "PARTNAME";
    public static final String PART_TYPE = "PARTTYPE";
    public static final String PART_ACTIVE = "PARTACTIVE";
    public static final String PART_PAINTABLE = "PARTPAINTABLE";
    public static final String PARENT_CHILD_MAPPING = "PARENTCHILDMAPPING";
    public static final String REPLACE_REPAIR = "REPLACEREPAIR";
    public static final String PAINT_INTENSITY = "PAINTINTENSITY";


    //FOR ASSESSMENT / COMPUTATION
    public static final String IS_BILLED_AMOUNT_CHANGED = "IS_BILLED_AMOUNT_CHANGED";
    public static final String IS_ASSESSED_AMOUNT_CHANGED = "IS_ASSESSED_AMOUNT_CHANGED";
    public static final String IS_REMOVE_REFIT_BILLED_AMOUNT_CHANGED = "IS_REMOVE_REFIT_BILLED_AMOUNT_CHANGED";
    public static final String IS_REMOVE_REFIT_ASSESSED_AMOUNT_CHANGED = "IS_REMOVE_REFIT_ASSESSED_AMOUNT_CHANGED";
    public static final String IS_PAINT_BILLED_AMOUNT_CHANGED = "IS_PAINT_BILLED_AMOUNT_CHANGED";

    public static final String UPDATED_SERVICE_TAX = "UPDATED_SERVICE_TAX";
    public static final String UPDATED_PART_TAX = "UPDATED_PART_TAX";
    public static final String UPDATED_OTHER_TAX = "UPDATED_OTHER_TAX";
    public static final String PART_PRICE_ASSESSED = "PART_PRICE_ASSESSED";
    public static final String PART_PRICE_NET = "PART_PRICE_NET";

    public static final String REMOVE_REFIT_PRICE_ASSESSED = "REMOVE_REFIT_PRICE_ASSESSED";
    public static final String REMOVE_REFIT_PRICE_NET = "REMOVE_REFIT_PRICE_NET";

    public static final String REPAIR_PRICE_ASSESSED = "REPAIR_PRICE_ASSESSED";
    public static final String REPAIR_PRICE_NET = "REPAIR_PRICE_NET";

    public static final String REPLACE_PRICE_BILLED = "REPLACE_PRICE_BILLED";
    public static final String REPAIR_PRICE_BILLED = "REPAIR_PRICE_BILLED";
    public static final String PAINT_PART_PRICE_BILLED = "PAINT_PART_PRICE_BILLED";

    public static final String PAINT_PART_PRICE_ASSESSED = "PAINT_PART_PRICE_ASSESSED";
    public static final String PAINT_PART_PRICE_NET = "PAINT_PART_PRICE_NET";

    public static final String PAINT_MATERIAL = "PAINT_MATERIAL";
    public static final String PAINT_LABOUR = "PAINT_LABOUR";

    public static final String PARTS_TAX_AMOUNT_PAINT = "PARTS_TAX_AMOUNT_PAINT";
    public static final String PARTS_TAX_AMOUNT = "PARTS_TAX_AMOUNT";
    public static final String SERVICE_TAX_AMOUNT_PAINT = "SERVICE_TAX_AMOUNT_PAINT";
    public static final String SERVICE_TAX_AMOUNT_REPLACE = "SERVICE_TAX_AMOUNT_REPLACE";
    public static final String LABOUR_OTHER_TAX_AMOUNT_PAINT = "LABOUR_OTHER_TAX_AMOUNT_PAINT";
    public static final String LABOUR_OTHER_TAX_AMOUNT_REPLACE = "LABOUR_OTHER_TAX_AMOUNT_REPLACE";
    public static final String SERVICE_TAX_AMOUNT_REPAIR = "SERVICE_TAX_AMOUNT_REPAIR";
    public static final String LABOUR_OTHER_TAX_AMOUNT_REPAIR = "LABOUR_OTHER_TAX_AMOUNT_REPAIR";
    public static final String DEPRECIATION_AMOUNT = "DEPRECIATION_AMOUNT";
    public static final String DEPRECIATION_AMOUNT_PAINT = "DEPRECIATION_AMOUNT_PAINT";


    public static final String DOCUMENT_MAP_TABLE = "DOCUMENT_MAP_TABLE";
    public static final String CLAIM_NO = "claimNo";
    //public static final String DOC_ID = "labour_other_tax";
    public static final String IMAGE_PATH = "imagePath";

    private void doUpdate0() {

        String tbl_SmsLastSync = "CREATE TABLE IF NOT EXISTS " + SMS_LAST_SYNC_TABLE + "(" + PRIMARY_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SMS_CLAIM_NO + " TEXT," + SMS_SYNC_DATETIME + " TEXT);";
        this.execute(tbl_SmsLastSync);

        String tbl_SmsDetails = "CREATE TABLE IF NOT EXISTS " + SMS_DETAILS_TABLE + "(" + PRIMARY_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SMS_CLAIM_NO + " TEXT," + SMS_MOBILE_NO + " TEXT," + SMS_SENDER + " TEXT," + SMS_BODY + " TEXT," + SMS_DATE + " TEXT);";
        this.execute(tbl_SmsDetails);

        String tbl_DocumentMaster = "CREATE TABLE IF NOT EXISTS " + DOCUMENT_MASTER_TABLE + "(" + PRIMARY_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + DOC_ID + " TEXT," + DOC_NAME + " TEXT," + DOC_CAT + " TEXT);";
        this.execute(tbl_DocumentMaster);

        String tbl_DocumentMap = "CREATE TABLE IF NOT EXISTS " + DOCUMENT_MAP_TABLE + "(" + PRIMARY_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CLAIM_NO + " TEXT," + DOC_ID + " TEXT," + DOC_NAME + " TEXT," + IMAGE_PATH + " TEXT);";
        this.execute(tbl_DocumentMap);

        String tbl_LastSyncInfo = "CREATE TABLE IF NOT EXISTS " + LAST_SYNC_INFO_TABLE + " ("
                + MASTER_NAME + " text primary key,"
                + SYNC_DATE + " text)";
        this.execute(tbl_LastSyncInfo);

        String tbl_ClaimSyncInfo = "CREATE TABLE IF NOT EXISTS " + CLAIM_SYNC_DATE_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + LAST_SYNC_DATE_TIME + " text,"
                + SYNC_DATE_TIME + " text)";
        this.execute(tbl_ClaimSyncInfo);

        String tbl_Dashboard = "CREATE TABLE IF NOT EXISTS " + DASHBOARD_CLAIM_TABLE + " (" + MASTER_CLAIM_NUMBER + " text primary key ,"
                + VEHICLE_REGISTRATION_NO + " text,"
                + VEHICLE_MODEL + " text,"
                + INSURED_NAME + " text,"
                + TAT + " text,"
                + WORKSHOP_NAME + " text,"
                + CLAIM_STAGE + " text,"
                + SERIAL_NO + " text,"
                + START_TIME + " text,"
                + CLAIM_TYPE + " text,"
                + INSURED_CITY + " text,"
                + EMAIL + " text,"
                + MOBILE + " text,"
                + INBOX_STATUS + " text,"
                + CLAIM_STATUS + " text,"
                + COMPLETION_PERCENTAGE + " text,"
                + COLOR_CODING + " text,"
                + POLICY_NUMBER + " text,"
                + SURVEYOR_CODE + " text,"
                + CLAIM_NUMBER + " text)";
        this.execute(tbl_Dashboard);

        String tbl_FlagDetails = "CREATE TABLE IF NOT EXISTS " + FLAG_DETAIL_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text,"
                + FLAG_CODE + " text,"
                + FLAG_STATUS + " text,"
                + FLAG_DESCRIPTION + " text,"
                + "  PRIMARY KEY ( " + MASTER_CLAIM_NUMBER + "," + FLAG_CODE + "))";
        this.execute(tbl_FlagDetails);

        String tbl_InsuredDetails = "CREATE TABLE IF NOT EXISTS " + INSURED_DETAIL_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + INSURED_NAME + " text,"
                + INSURED_ADDRESS + " text,"
                + INSURED_PIN_CODE + " text,"
                + INSURED_MOBILE_NUMBER + " text,"
                + CUSTOMER_ID + " text,"
                + INSURED_EMAIL + " text)";
        this.execute(tbl_InsuredDetails);

        String tbl_dlNrc = "CREATE TABLE IF NOT EXISTS " + DL_N_RC_DETAIL_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + HAS_LICENSE + " text,"
                + HAS_FIR + " text,"
                + DRIVER_NAME + " text,"
                + DRIVER_DOB + " text,"
                + DRIVER_TYPE_ID + " text,"
                + DRIVER_TYPE + " text,"
                + DRIVER_GENDER + " text,"
                + ISSUANCE_DATE + " text,"
                + EXPIRY_DATE + " text,"
                + LICENSE_NUMBER + " text,"
                + LICENSE_TYPE + " text,"
                + ISSUANCE_RTO + " text,"
                + INSURED_NAME + " text,"
                + VEHICLE_REGISTRATION_NO + " text,"
                + DATE_OF_VEHICLE_REGISTRATION + " text,"
                + TRANSFER_DATE + " text,"
                + VEHICLE_MAKE + " text,"
                + VEHICLE_MODEL + " text,"
                + REFINED_VEHICLE_MAKE + " text,"
                + REFINED_VEHICLE_MODEL + " text,"
                + ENGINE_NUMBER + " text,"
                + CHASSIS_NUMBER + " text,"
                + VEHICLE_COLOR + " text,"
                + VEHICLE_COLOR_TYPE + " text,"
                + ODOMETER_READING + " text,"
                + RTO_NAME + " text,"
                + FIR_DATE + " text,"
                + POLICE_STATION + " text,"
                + FIR_UNDER_SECTION + " text)";
        this.execute(tbl_dlNrc);

        String tbl_IssuanceRTOMaster = "CREATE TABLE IF NOT EXISTS " + ISSUANCE_RTO_MASTER_TABLE + " ("
                + ISSUANCE_RTO + " text)";
        this.execute(tbl_IssuanceRTOMaster);

        String tbl_DriverTypeMaster = "CREATE TABLE IF NOT EXISTS " + DRIVER_TYPE_MASTER_TABLE + " ("
                + DRIVER_TYPE_ID + " text primary key,"
                + DRIVER_TYPE + " text)";
        this.execute(tbl_DriverTypeMaster);

        String tbl_WorkshopMaster = "CREATE TABLE IF NOT EXISTS " + WORKSKHOP_MASTER_TABLE + " ("
                + WORKSHOP_ID + " text primary key,"
                + WORKSHOP_NAME + " text,"
                + WORKSHOP_MOBILE + " text,"
                + WORKSHOP_ADDRESS + " text,"
                + WORKSHOP_PAYMENT_MODE + " text,"
                + WORKSHOP_TYPE_CD + " text,"
                + WORKSHOP_CITY + " text)";
        this.execute(tbl_WorkshopMaster);

        String tbl_BrekinInfo = "CREATE TABLE IF NOT EXISTS " + BREAKIN_INFO_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + BREAKIN_NUMBER + " text,"
                + BREAKIN_DECISION + " text,"
                + BREAKIN_APPROVAL_DATE + " text,"
                + BREAKIN_REMARKS + " text)";
        this.execute(tbl_BrekinInfo);

        String tbl_BrekinImages = "CREATE TABLE IF NOT EXISTS " + BREAKIN_IMAGES_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text,"
                + BREAKIN_ID + " text,"
                + BREAKIN_IMAGE_NAME + " text,"
                + BREAKIN_IMAGE_BASE64 + " text)";
        this.execute(tbl_BrekinImages);

        String tbl_CimaRemarks = "CREATE TABLE IF NOT EXISTS " + CIMA_REMARKS_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + CIMA_REMARKS + " text)";
        this.execute(tbl_CimaRemarks);

        String tbl_PastClaimDetails = "CREATE TABLE IF NOT EXISTS " + PAST_CLAIM_DETAILS_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + PAST_CLAIM_DETAILS_JSON_STRING + " text)";
        this.execute(tbl_PastClaimDetails);

        String tbl_PastClaimImages = "CREATE TABLE IF NOT EXISTS " + PAST_CLAIM_IMAGES_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text,"
                + DOC_ID + " text primary key,"
                + DOC_NAME + " text)";
        this.execute(tbl_PastClaimImages);

        String tbl_PastClaimImagesBase64 = "CREATE TABLE IF NOT EXISTS " + PAST_CLAIM_IMAGES_BASE64_TABLE + " ("
                + DOC_ID + " text,"
                + BASE_64 + " text)";
        this.execute(tbl_PastClaimImagesBase64);

        String tbl_PastClaimImagesHasData = "CREATE TABLE IF NOT EXISTS " + PAST_CLAIM_IMAGES_HAS_DATA_TABLE + " ("
                + DOC_ID + " text primary key,"
                + HAS_DATA + " text)";
        this.execute(tbl_PastClaimImagesHasData);

        String tbl_SurveyDetails = "CREATE TABLE IF NOT EXISTS " + SURVEY_DETAILS_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + SURVEY_START_DATE + " text,"
                + SURVEY_TIME + " text,"
                + SURVEY_ESTIMATE_DATE + " text,"
                + SURVEY_REINSPECTION_DATE + " text,"
                + SURVEY_LAST_DOCUMENT_READ_DATE + " text,"
                + VEHICLE_REPORT_DATE + " text,"
                + VEHICLE_REPORT_TIME + " text,"
                + SURVEY_COMPUTED_AMOUNT + " text,"
                + SURVEY_ESTIMATED_AMOUNT + " text,"
                + SURVEY_REMARKS + " text,"
                + SURVEY_SALVAGE_INVOLVED + " text,"
                + SURVEY_TOTAL_LOSS + " text)";
        this.execute(tbl_SurveyDetails);

        String tbl_LossTypeMaster = "CREATE TABLE IF NOT EXISTS " + LOSS_TYPE_MASTER_TABLE + " ("
                + SEQUENCE_NO + " text primary key,"
                + LOSS_REASON + " text)";
        this.execute(tbl_LossTypeMaster);

        String tbl_VehicleColorMaster = "CREATE TABLE IF NOT EXISTS " + VEHICLE_COLOR_MASTER_TABLE + " ("
                + VEHICLE_COLOR_CODE + " text primary key,"
                + VEHICLE_COLOR + " text)";
        this.execute(tbl_VehicleColorMaster);

        String tbl_VehicleColorTypeMaster = "CREATE TABLE IF NOT EXISTS " + VEHICLE_COLOR_TYPE_MASTER_TABLE + " ("
                + VEHICLE_COLOR_TYPE_ID + " text primary key,"
                + VEHICLE_COLOR_TYPE + " text)";
        this.execute(tbl_VehicleColorTypeMaster);

        String tbl_PointOfImpactHasData = "CREATE TABLE IF NOT EXISTS " + POINT_OF_IMPACT_HAS_DATA_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + HAS_DATA + " text)";
        this.execute(tbl_PointOfImpactHasData);

        String tbl_PointOfImpactData = "CREATE TABLE IF NOT EXISTS " + POINT_OF_IMPACT_DATA_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text,"
                + PART_CODE_POI + " text,"
                + PART_NAME_POI + " text,"
                + INTENSITY_POI + " text)";
        this.execute(tbl_PointOfImpactData);

        String tbl_CPLoss = "CREATE TABLE IF NOT EXISTS " + CP_LOSS_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text,"
                + MASTER_CLAIM_NUMBER_PAYEE + " text,"
                + PAYEE_TYPE + " text,"
                + PAYEE_NAME + " text,"
                + PAYEE_ADDRESS + " text,"
                + SERVICE_TAX + " text,"
                + TDS + " text,"
                + NET_AMOUNT + " text,"
                + PAYEE_TYPE_ID + " text,"
                + PAYEE_ID + " text,"
                + FLAG + " text,"
                + ON_ACCOUNT_FLAG + " text,"
                + CHEQUE_ISSUED_FLAG + " text,"
                + PAYMENT_SERIAL_NO + " text,"
                + ACCOUNT_ID + " text,"
                + CLMNT_NAME + " text,"
                + PAY_TYPE + " text,"
                + PAYMENT_MODE + " text)";
        this.execute(tbl_CPLoss);

        String tbl_ClaimantDetails = "CREATE TABLE IF NOT EXISTS " + CLAIMANT_DETAILS_TABLE + " ("
                + CLAIMANT_CODE + " text primary key,"
                + CLAIMANT_NAME + " text,"
                + CLAIMANT_ADDRESS + " text)";
        this.execute(tbl_ClaimantDetails);

        String tbl_NEFT = "CREATE TABLE IF NOT EXISTS " + NEFT_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + HAS_DATA + " text,"
                + IFSC_CODE + " text,"
                + BENEFICIARY_NAME + " text,"
                + BENEFICIARY_ADDRESS + " text,"
                + PHONE_NUMBER + " text,"
                + MOBILE_NUMBER + " text,"
                + MICR_CODE + " text,"
                + ACCOUNT_NAME + " text,"
                + ACCOUNT_NUMBER + " text,"
                + BANK_NAME + " text,"
                + BRANCH_NAME + " text,"
                + EMAIL_ID + " text)";
        this.execute(tbl_NEFT);

        String tbl_Inured_Existing_Rejected = "CREATE TABLE IF NOT EXISTS " + INSURED_EXISTING_REJECTED_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text,"
                + HAS_DATA + " text,"
                + IS_REJECTED + " text,"
                + IFSC_CODE + " text,"
                + CUSTOMER_ID + " text,"
                + CUSTOMER_NAME + " text,"
                + ACCOUNT_ID + " text,"
                + ACCOUNT_NAME + " text,"
                + ACCOUNT_NUMBER + " text,"
                + BANK_NAME + " text,"
                + BRANCH_NAME + " text,"
                + EMAIL_ID + " text)";
        this.execute(tbl_Inured_Existing_Rejected);

        String tbl_Beneficiary_Existing_Rejected = "CREATE TABLE IF NOT EXISTS " + BENEFICIARY_EXISTING_REJECTED_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text,"
                + HAS_DATA + " text,"
                + IS_REJECTED + " text,"
                + IFSC_CODE + " text,"
                + CUSTOMER_ID + " text,"
                + CUSTOMER_NAME + " text,"
                + ACCOUNT_ID + " text,"
                + ACCOUNT_NAME + " text,"
                + ACCOUNT_NUMBER + " text,"
                + BANK_NAME + " text,"
                + BRANCH_NAME + " text,"
                + EMAIL_ID + " text,"

                + BENEFICIARY_NAME + " text,"
                + BENEFICIARY_ADDRESS + " text,"
                + ISD_CODE + " text,"
                + STD_CODE + " text,"
                + PHONE_NUMBER + " text,"
                + MOBILE_NUMBER + " text,"
                + MOBILE_ISD + " text,"
                + MICR_CODE + " text,"
                + ACTIVE + " text,"
                + ACCOUNT_STATUS + " text)";
        this.execute(tbl_Beneficiary_Existing_Rejected);

        String tbl_CPExpense = "CREATE TABLE IF NOT EXISTS " + CP_EXPENSE_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text,"
                + MASTER_CLAIM_NUMBER_PAYEE + " text,"
                + PAYEE_TYPE + " text,"
                + PAYEE_NAME + " text,"
                + PAYEE_ADDRESS + " text,"
                + PROFESSIONAL_FEES + " text,"
                + EXPENSE + " text,"
                + SERVICE_TAX + " text,"
                + TOTAL_BILL + " text,"
                + TDS + " text,"
                + CHEQUE_AMOUNT + " text,"
                + EXPENSE_BILL_DATE + " text,"
                + SERVICE_TAX_APPLICABILITY + " text,"
                + PAYEE_TYPE_ID + " text,"
                + PAYEE_ID + " text,"
                + VENDOR_TYPE_ID + " text,"
                + VENDOR_TYPE_DESCRIPTION + " text,"
                + VENDOR_ID + " text,"
                + VENDOR_NAME + " text,"
                + VENDOR_ADDRESS + " text,"
                + NUMERIC_PAYMENT_SERIAL_NO + " text,"
                + ON_ACCOUNT_FLAG + " text,"
                + FLAG + " text,"
                + CHEQUE_ISSUED_FLAG + " text,"
                + PAYMENT_MODE + " text)";
        this.execute(tbl_CPExpense);

        String tbl_ResourceMaster = "CREATE TABLE IF NOT EXISTS " + RESOURCE_MASTER_TABLE + " ("
                + RESOURCE_ID + " text primary key,"
                + RESOURCE_NAME + " text,"
                + RESOURCE_ADDRESS + " text,"
                + RESOURCE_PAYMENT_MODE + " text)";
        this.execute(tbl_ResourceMaster);

        String tbl_WorkshopSelection = "CREATE TABLE IF NOT EXISTS " + WORKSHOP_SELECTION_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + HAS_DATA + " text,"
                + HAS_WORKSHOP_TWO + " text,"
                + SURVEY_LOCATION + " text,"
                + WORKSHOP_ID + " text,"
                + WORKSHOP_NAME + " text,"
                + WORKSHOP_ADDRESS + " text,"
                + WORKSHOP_CITY + " text,"
                + INVOICE_NUMBER + " text,"
                + INVOICE_DATE + " text,"
                + INVOICE_AMOUNT + " text,"
                + BILL_SUBMIT_DATE + " text,"
                + REPAIR_DETAILS + " text)";
        this.execute(tbl_WorkshopSelection);

        String tbl_WorkshopSelectionTwo = "CREATE TABLE IF NOT EXISTS " + WORKSHOP_SELECTION_TWO_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + HAS_DATA + " text,"
                + WORKSHOP_ID + " text,"
                + WORKSHOP_NAME + " text,"
                + WORKSHOP_ADDRESS + " text,"
                + WORKSHOP_CITY + " text,"
                + VEHICLE_REPORT_DATE + " text,"
                + VEHICLE_REPORT_TIME + " text,"
                + INVOICE_NUMBER + " text,"
                + INVOICE_DATE + " text,"
                + INVOICE_AMOUNT + " text,"
                + BILL_SUBMIT_DATE + " text,"
                + REPAIR_DETAILS + " text)";
        this.execute(tbl_WorkshopSelectionTwo);

        String tbl_PreApproval = "CREATE TABLE IF NOT EXISTS " + PRE_APPROVAL_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + PRE_APPROVAL_json + " text)";
        this.execute(tbl_PreApproval);

        String tbl_ClaimStageMaster = "CREATE TABLE IF NOT EXISTS " + CLAIM_STAGE_MASTER_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text,"
                + CLAIM_STAGE + " text)";
        this.execute(tbl_ClaimStageMaster);

        String tbl_ClaimStage = "CREATE TABLE IF NOT EXISTS " + CLAIM_STAGE_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + CLAIM_STAGE + " text,"
                + CLAIM_STAGE_REMARK + " text)";
        this.execute(tbl_ClaimStage);

        String tbl_PreviousRemarksTable = "CREATE TABLE IF NOT EXISTS " + PREVIOUS_REMARKS_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text,"
                + DATE + " text,"
                + CLAIM_STAGE_REMARK + " text,"
                + SURVEYOR + " text)";
        this.execute(tbl_PreviousRemarksTable);

        String tbl_LicenseTypeMaster = "CREATE TABLE IF NOT EXISTS " + LICENSE_TYPE_MASTER_TABLE + " ("
                + LICENSE_TYPE_ID + " text primary key,"
                + LICENSE_DISPLAY_NAME + " text,"
                + LICENSE_TYPE + " text,"
                + PRODUCT + " text)";
        this.execute(tbl_LicenseTypeMaster);

        String tbl_PolicyInfo = "CREATE TABLE IF NOT EXISTS " + POLICY_INFO_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + POLICY_INFO_JSON_STRING + " text)";
        this.execute(tbl_PolicyInfo);

        String tbl_ClaimDetails = "CREATE TABLE IF NOT EXISTS " + CLAIM_DETAILS_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + IDV_VALUE + " text,"
                + LOSS_DATE + " text,"
                + LOSS_TIME + " text,"
                + DATE_OF_REGISTRATION + " text,"
                + DATE_OF_NOTIFICATION + " text,"
                + POLICY_START_DATE + " text,"
                + POLICY_END_DATE + " text)";
        this.execute(tbl_ClaimDetails);

        String tbl_DepreciationMaster = "CREATE TABLE IF NOT EXISTS " + DEPRECIATION_MASTER_TABLE + " ("
                + DEPRECIATION_PERCENTAGE + " text primary key,"
                + DEPRECIATION + " text,"
                + AGE_FROM + " text,"
                + AGE_TO + " text)";
        this.execute(tbl_DepreciationMaster);

        String tbl_DiscountMaster = "CREATE TABLE IF NOT EXISTS " + DISCOUNT_MASTER_TABLE + " ("
                + MAKE + " text,"
                + DISCOUNT_TYPE + " text,"
                + SERIAL_NUMBER + " text,"
                + FROM + " text,"
                + TO + " text,"
                + DISCOUNT_PERCENTAGE + " text)";
        this.execute(tbl_DiscountMaster);

        String tbl_CityGroupMaster = "CREATE TABLE IF NOT EXISTS " + CITY_GROUP_MASTER_TABLE + " ("
                + MANUFACTURER + " text,"
                + CITY_NAME + " text,"
                + GROUP + " text)";
        this.execute(tbl_CityGroupMaster);

        String tbl_ComputationDataEntryCommon = "CREATE TABLE IF NOT EXISTS " + COMPUTATION_DATA_ENTRY_COMMON_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + COMPULSARY_EXCESS + " text,"
                + VOLUNTARY_EXCESS + " text,"
                + SALVAGE_AMOUNT + " text,"
                + DEPRECIATION_PERCENTAGE + " text)";
        this.execute(tbl_ComputationDataEntryCommon);

        String tbl_AddonDetails = "CREATE TABLE IF NOT EXISTS " + ADDON_DETAILS_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + SI_VALUE + " text,"
                + DEDUCTIBLE + " text,"
                + BENEFIT + " text,"
                + COMPUTED_AMOUNT + " text,"
                + AMOUNT_PER_DAY + " text,"
                + IDV_VALUE + " text,"
                + INVOICE_VALUE + " text,"
                + FIRST_TIME_REGISTRATION + " text,"
                + TOTAL_VALUE + " text,"
                + TOTAL_PAYABLE_UNDER_RTI + " text,"
                + REPAIR_START_DATE + " text,"
                + REPAIR_END_DATE + " text,"
                + EMI_TO_BE_PAID + " text,"
                + EMI_PROTECTOR + " text,"
                + TOTAL_PAYABLE + " text,"
                + HIGHER_PROTECTION_REMOVAL_COST + " text)";
        this.execute(tbl_AddonDetails);

        String tbl_ClaimProcessing = "CREATE TABLE IF NOT EXISTS " + CLAIM_PROCESSING_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + INSURED_DETAILS + " text,"
                + CLAIM_HISTORY + " text,"
                + SURVEY_DETAILS + " text,"
                + POINT_OF_IMPACT + " text,"
                + WORKSHOP_DETAILS + " text,"
                + DL_N_RC_DETAILS + " text,"
                + UPLOAD_DOCUMENTS + " text,"
                + COMPUTATION_DATA_ENTRY + " text,"
                + CP_LOSS + " text,"
                + CP_EXPENSE + " text)";
        this.execute(tbl_ClaimProcessing);

        String tbl_ComputationDataEntryWorkshopOne = "CREATE TABLE IF NOT EXISTS " + COMPUTATION_DATAENTRY_WORKSHOPONE_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key ,"
                + PARTS_TAX_RATE + " text,"
                + PARTS_OTHER_TAX + " text,"
                + LABOUR_OTHER_TAX + " text,"
                + HAS_DATA + " text,"
                + PAINT_MATERIAL_BREAKDOWN + " text,"
                + PAINT_TAX_LOADING + " text)";
        this.execute(tbl_ComputationDataEntryWorkshopOne);

        String tbl_ComputationDataEntryWorkshopTwo = "CREATE TABLE IF NOT EXISTS " + COMPUTATION_DATAENTRY_WORKSHOPTWO_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key ,"
                + PARTS_TAX_RATE + " text,"
                + PARTS_OTHER_TAX + " text,"
                + LABOUR_OTHER_TAX + " text,"
                + HAS_DATA + " text,"
                + PAINT_MATERIAL_BREAKDOWN + " text,"
                + PAINT_TAX_LOADING + " text)";
        this.execute(tbl_ComputationDataEntryWorkshopTwo);

        String tbl_PartsWorkshopOne = "CREATE TABLE IF NOT EXISTS " + PARTS_WORKSHOP_ONE_TABLE + " (" + MASTER_CLAIM_NUMBER + " text,"
                + PART_CODE + " text,"
                + SUB_BUCKET + " text,"
                + PART_NAME + " text,"
                + PART_TYPE + " text,"
                + PART_ACTIVE + " text,"
                + PART_PAINTABLE + " text,"
                + PARENT_CHILD_MAPPING + " text,"
                + REPLACE_REPAIR + " text,"
                + PAINT_INTENSITY + " text,"
                + "  PRIMARY KEY ( " + MASTER_CLAIM_NUMBER + "," + PART_CODE + "))";
        this.execute(tbl_PartsWorkshopOne);

        String tbl_PartsWorkshopTwo = "CREATE TABLE IF NOT EXISTS " + PARTS_WORKSHOP_TWO_TABLE + " (" + MASTER_CLAIM_NUMBER + " text,"
                + PART_CODE + " text,"
                + SUB_BUCKET + " text,"
                + PART_NAME + " text,"
                + PART_TYPE + " text,"
                + PART_ACTIVE + " text,"
                + PART_PAINTABLE + " text,"
                + PARENT_CHILD_MAPPING + " text,"
                + REPLACE_REPAIR + " text,"
                + PAINT_INTENSITY + " text,"
                + "  PRIMARY KEY ( " + MASTER_CLAIM_NUMBER + "," + PART_CODE + "))";
        this.execute(tbl_PartsWorkshopTwo);

        String tbl_PartsMaster = "CREATE TABLE IF NOT EXISTS " + PART_NAME_MASTER_TABLE + " ("
                + PART_CODE + " text,"
                + SUB_BUCKET + " text,"
                + PART_NAME + " text,"
                + PART_TYPE + " text,"
                + PART_ACTIVE + " text,"
                + PART_PAINTABLE + " text,"
                + PARENT_CHILD_MAPPING + " text)";
        this.execute(tbl_PartsMaster);

        String tbl_PartsRateMaster = "CREATE TABLE IF NOT EXISTS " + PART_PRICE_MASTER_TABLE + " ("
                + PART_CODE + " text,"
                + CAR_MAKE + " text,"
                + CAR_MODEL + " text,"
                + YEAR_FROM + " text,"
                + YEAR_TO + " text,"
                + PART_PRICE + " text,"
                + REMOVE_REFIT_PRICE + " text)";
        this.execute(tbl_PartsRateMaster);

        String tbl_PaintRateMaster = "CREATE TABLE IF NOT EXISTS " + PAINT_PRICE_MASTER_TABLE + " ("
                + PART_CODE + " text,"
                + CAR_MAKE + " text,"
                + CAR_MODEL + " text,"
                + YEAR_FROM + " text,"
                + YEAR_TO + " text,"

                + PAINT_FULL_SOLID_GROUP1 + " text,"
                + PAINT_FULL_METALLIC_GROUP1 + " text,"
                + PAINT_FULL_PEARL_GROUP1 + " text,"
                + PAINT_FULL_MATTE_GROUP1 + " text,"
                + PAINT_PARTIAL_SOLID_GROUP1 + " text,"
                + PAINT_PARTIAL_METALLIC_GROUP1 + " text,"
                + PAINT_PARTIAL_PEARL_GROUP1 + " text,"
                + PAINT_PARTIAL_MATTE_GROUP1 + " text,"

                + PAINT_FULL_SOLID_GROUP2 + " text,"
                + PAINT_FULL_METALLIC_GROUP2 + " text,"
                + PAINT_FULL_PEARL_GROUP2 + " text,"
                + PAINT_FULL_MATTE_GROUP2 + " text,"
                + PAINT_PARTIAL_SOLID_GROUP2 + " text,"
                + PAINT_PARTIAL_METALLIC_GROUP2 + " text,"
                + PAINT_PARTIAL_PEARL_GROUP2 + " text,"
                + PAINT_PARTIAL_MATTE_GROUP2 + " text,"

                + PAINT_FULL_SOLID_GROUP3 + " text,"
                + PAINT_FULL_METALLIC_GROUP3 + " text,"
                + PAINT_FULL_PEARL_GROUP3 + " text,"
                + PAINT_FULL_MATTE_GROUP3 + " text,"
                + PAINT_PARTIAL_SOLID_GROUP3 + " text,"
                + PAINT_PARTIAL_METALLIC_GROUP3 + " text,"
                + PAINT_PARTIAL_PEARL_GROUP3 + " text,"
                + PAINT_PARTIAL_MATTE_GROUP3 + " text,"

                + PAINT_FULL_SOLID_GROUP4 + " text,"
                + PAINT_FULL_METALLIC_GROUP4 + " text,"
                + PAINT_FULL_PEARL_GROUP4 + " text,"
                + PAINT_FULL_MATTE_GROUP4 + " text,"
                + PAINT_PARTIAL_SOLID_GROUP4 + " text,"
                + PAINT_PARTIAL_METALLIC_GROUP4 + " text,"
                + PAINT_PARTIAL_PEARL_GROUP4 + " text,"
                + PAINT_PARTIAL_MATTE_GROUP4 + " text )";
        this.execute(tbl_PaintRateMaster);

        String tbl_SelectedPartsWorkshopOne = "CREATE TABLE IF NOT EXISTS " + SELECTED_PARTS_WORKSHOP_ONE_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text,"
                + PART_CODE + " text,"
                + SUB_BUCKET + " text,"
                + PART_NAME + " text,"
                + PART_TYPE + " text,"
                + PART_ACTIVE + " text,"
                + PART_PAINTABLE + " text,"
                + PARENT_CHILD_MAPPING + " text,"

                + CAR_MAKE + " text,"
                + CAR_MODEL + " text,"
                + YEAR_FROM + " text,"
                + YEAR_TO + " text,"
                + PART_PRICE + " text,"
                + IS_BILLED_AMOUNT_CHANGED + " text,"
                + IS_ASSESSED_AMOUNT_CHANGED + " text,"
                + IS_REMOVE_REFIT_BILLED_AMOUNT_CHANGED + " text,"
                + IS_REMOVE_REFIT_ASSESSED_AMOUNT_CHANGED + " text,"
                + IS_PAINT_BILLED_AMOUNT_CHANGED + " text,"
                + PART_PRICE_ASSESSED + " text,"
                + PART_PRICE_NET + " text,"
                + REMOVE_REFIT_PRICE + " text,"
                + REMOVE_REFIT_PRICE_ASSESSED + " text,"
                + REMOVE_REFIT_PRICE_NET + " text,"
                + REPAIR_PRICE_ASSESSED + " text,"
                + REPAIR_PRICE_NET + " text,"
                + REPLACE_REPAIR + " text,"
                + PAINT_INTENSITY + " text,"

                + UPDATED_SERVICE_TAX + " text,"
                + UPDATED_PART_TAX + " text,"
                + UPDATED_OTHER_TAX + " text,"
                + PARTS_TAX_RATE + " text,"
                + SERVICE_TAX + " text,"
                + LABOUR_OTHER_TAX + " text,"
                + DEPRECIATION_PERCENTAGE + " text,"

                + PARTS_TAX_AMOUNT_PAINT + " text,"
                + PARTS_TAX_AMOUNT + " text,"
                + SERVICE_TAX_AMOUNT_REPLACE + " text,"
                + SERVICE_TAX_AMOUNT_PAINT + " text,"
                + LABOUR_OTHER_TAX_AMOUNT_PAINT + " text,"
                + LABOUR_OTHER_TAX_AMOUNT_REPLACE + " text,"
                + SERVICE_TAX_AMOUNT_REPAIR + " text,"
                + LABOUR_OTHER_TAX_AMOUNT_REPAIR + " text,"
                + DEPRECIATION_AMOUNT + " text,"
                + DEPRECIATION_AMOUNT_PAINT + " text,"

                + REPLACE_PRICE_BILLED + " text,"
                + REPAIR_PRICE_BILLED + " text,"
                + PAINT_PART_PRICE_BILLED + " text,"
                + PAINT_PART_PRICE_ASSESSED + " text,"
                + PAINT_PART_PRICE_NET + " text,"
                + PAINT_LABOUR + " text,"
                + PAINT_MATERIAL + " text,"

                + PAINT_FULL_SOLID_GROUP1 + " text,"
                + PAINT_FULL_METALLIC_GROUP1 + " text,"
                + PAINT_FULL_PEARL_GROUP1 + " text,"
                + PAINT_FULL_MATTE_GROUP1 + " text,"
                + PAINT_PARTIAL_SOLID_GROUP1 + " text,"
                + PAINT_PARTIAL_METALLIC_GROUP1 + " text,"
                + PAINT_PARTIAL_PEARL_GROUP1 + " text,"
                + PAINT_PARTIAL_MATTE_GROUP1 + " text,"

                + PAINT_FULL_SOLID_GROUP2 + " text,"
                + PAINT_FULL_METALLIC_GROUP2 + " text,"
                + PAINT_FULL_PEARL_GROUP2 + " text,"
                + PAINT_FULL_MATTE_GROUP2 + " text,"
                + PAINT_PARTIAL_SOLID_GROUP2 + " text,"
                + PAINT_PARTIAL_METALLIC_GROUP2 + " text,"
                + PAINT_PARTIAL_PEARL_GROUP2 + " text,"
                + PAINT_PARTIAL_MATTE_GROUP2 + " text,"

                + PAINT_FULL_SOLID_GROUP3 + " text,"
                + PAINT_FULL_METALLIC_GROUP3 + " text,"
                + PAINT_FULL_PEARL_GROUP3 + " text,"
                + PAINT_FULL_MATTE_GROUP3 + " text,"
                + PAINT_PARTIAL_SOLID_GROUP3 + " text,"
                + PAINT_PARTIAL_METALLIC_GROUP3 + " text,"
                + PAINT_PARTIAL_PEARL_GROUP3 + " text,"
                + PAINT_PARTIAL_MATTE_GROUP3 + " text,"

                + PAINT_FULL_SOLID_GROUP4 + " text,"
                + PAINT_FULL_METALLIC_GROUP4 + " text,"
                + PAINT_FULL_PEARL_GROUP4 + " text,"
                + PAINT_FULL_MATTE_GROUP4 + " text,"
                + PAINT_PARTIAL_SOLID_GROUP4 + " text,"
                + PAINT_PARTIAL_METALLIC_GROUP4 + " text,"
                + PAINT_PARTIAL_PEARL_GROUP4 + " text,"
                + PAINT_PARTIAL_MATTE_GROUP4 + " text,"

                + "  PRIMARY KEY ( " + MASTER_CLAIM_NUMBER + "," + PART_CODE + "," + PART_NAME + "))";
        this.execute(tbl_SelectedPartsWorkshopOne);

        String tbl_SelectedPartsWorkshopTwo = "CREATE TABLE IF NOT EXISTS " + SELECTED_PARTS_WORKSHOP_TWO_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text,"
                + PART_CODE + " text,"
                + SUB_BUCKET + " text,"
                + PART_NAME + " text,"
                + PART_TYPE + " text,"
                + PART_ACTIVE + " text,"
                + PART_PAINTABLE + " text,"
                + PARENT_CHILD_MAPPING + " text,"

                + CAR_MAKE + " text,"
                + CAR_MODEL + " text,"
                + YEAR_FROM + " text,"
                + YEAR_TO + " text,"
                + PART_PRICE + " text,"
                + IS_BILLED_AMOUNT_CHANGED + " text,"
                + IS_ASSESSED_AMOUNT_CHANGED + " text,"
                + IS_REMOVE_REFIT_BILLED_AMOUNT_CHANGED + " text,"
                + IS_REMOVE_REFIT_ASSESSED_AMOUNT_CHANGED + " text,"
                + IS_PAINT_BILLED_AMOUNT_CHANGED + " text,"
                + PART_PRICE_ASSESSED + " text,"
                + PART_PRICE_NET + " text,"
                + REMOVE_REFIT_PRICE + " text,"
                + REMOVE_REFIT_PRICE_ASSESSED + " text,"
                + REMOVE_REFIT_PRICE_NET + " text,"
                + REPAIR_PRICE_ASSESSED + " text,"
                + REPAIR_PRICE_NET + " text,"
                + REPLACE_REPAIR + " text,"
                + PAINT_INTENSITY + " text,"

                + UPDATED_SERVICE_TAX + " text,"
                + UPDATED_PART_TAX + " text,"
                + UPDATED_OTHER_TAX + " text,"
                + PARTS_TAX_RATE + " text,"
                + SERVICE_TAX + " text,"
                + LABOUR_OTHER_TAX + " text,"
                + DEPRECIATION_PERCENTAGE + " text,"

                + PARTS_TAX_AMOUNT_PAINT + " text,"
                + PARTS_TAX_AMOUNT + " text,"
                + SERVICE_TAX_AMOUNT_REPLACE + " text,"
                + SERVICE_TAX_AMOUNT_PAINT + " text,"
                + LABOUR_OTHER_TAX_AMOUNT_PAINT + " text,"
                + LABOUR_OTHER_TAX_AMOUNT_REPLACE + " text,"
                + SERVICE_TAX_AMOUNT_REPAIR + " text,"
                + LABOUR_OTHER_TAX_AMOUNT_REPAIR + " text,"
                + DEPRECIATION_AMOUNT + " text,"
                + DEPRECIATION_AMOUNT_PAINT + " text,"

                + REPLACE_PRICE_BILLED + " text,"
                + REPAIR_PRICE_BILLED + " text,"
                + PAINT_PART_PRICE_BILLED + " text,"
                + PAINT_PART_PRICE_ASSESSED + " text,"
                + PAINT_PART_PRICE_NET + " text,"
                + PAINT_LABOUR + " text,"
                + PAINT_MATERIAL + " text,"

                + PAINT_FULL_SOLID_GROUP1 + " text,"
                + PAINT_FULL_METALLIC_GROUP1 + " text,"
                + PAINT_FULL_PEARL_GROUP1 + " text,"
                + PAINT_FULL_MATTE_GROUP1 + " text,"
                + PAINT_PARTIAL_SOLID_GROUP1 + " text,"
                + PAINT_PARTIAL_METALLIC_GROUP1 + " text,"
                + PAINT_PARTIAL_PEARL_GROUP1 + " text,"
                + PAINT_PARTIAL_MATTE_GROUP1 + " text,"

                + PAINT_FULL_SOLID_GROUP2 + " text,"
                + PAINT_FULL_METALLIC_GROUP2 + " text,"
                + PAINT_FULL_PEARL_GROUP2 + " text,"
                + PAINT_FULL_MATTE_GROUP2 + " text,"
                + PAINT_PARTIAL_SOLID_GROUP2 + " text,"
                + PAINT_PARTIAL_METALLIC_GROUP2 + " text,"
                + PAINT_PARTIAL_PEARL_GROUP2 + " text,"
                + PAINT_PARTIAL_MATTE_GROUP2 + " text,"

                + PAINT_FULL_SOLID_GROUP3 + " text,"
                + PAINT_FULL_METALLIC_GROUP3 + " text,"
                + PAINT_FULL_PEARL_GROUP3 + " text,"
                + PAINT_FULL_MATTE_GROUP3 + " text,"
                + PAINT_PARTIAL_SOLID_GROUP3 + " text,"
                + PAINT_PARTIAL_METALLIC_GROUP3 + " text,"
                + PAINT_PARTIAL_PEARL_GROUP3 + " text,"
                + PAINT_PARTIAL_MATTE_GROUP3 + " text,"

                + PAINT_FULL_SOLID_GROUP4 + " text,"
                + PAINT_FULL_METALLIC_GROUP4 + " text,"
                + PAINT_FULL_PEARL_GROUP4 + " text,"
                + PAINT_FULL_MATTE_GROUP4 + " text,"
                + PAINT_PARTIAL_SOLID_GROUP4 + " text,"
                + PAINT_PARTIAL_METALLIC_GROUP4 + " text,"
                + PAINT_PARTIAL_PEARL_GROUP4 + " text,"
                + PAINT_PARTIAL_MATTE_GROUP4 + " text,"

                + "  PRIMARY KEY ( " + MASTER_CLAIM_NUMBER + "," + PART_CODE + "," + PART_NAME + "))";
        this.execute(tbl_SelectedPartsWorkshopTwo);

/*        String tbl_CustomPartCountOne = "CREATE TABLE IF NOT EXISTS " + CUSTOM_PARTS_COUNT_ONE_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + COUNT + " text)";
        this.execute(tbl_CustomPartCountOne);

        String tbl_CustomPartCountTwo = "CREATE TABLE IF NOT EXISTS " + CUSTOM_PARTS_COUNT_TWO_TABLE + " ("
                + MASTER_CLAIM_NUMBER + " text primary key,"
                + COUNT + " text)";
        this.execute(tbl_CustomPartCountTwo);*/
    }

    public SQLiteDatabase getConnection() {
        SQLiteDatabase dbCon = this.getWritableDatabase();
        return dbCon;
    }
}
