package edu.gatech.seclass.jobcompare6300;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    //Job Table:
    private static final String JOB_TABLE = "JOB_TABLE";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TITLE = "TITLE";
    private static final String COLUMN_COMPANY = "COMPANY";
    private static final String COLUMN_CITY = "CITY";
    private static final String COLUMN_STATE = "STATE";
    private static final String COLUMN_COL_INDEX = "COL_INDEX";
    private static final String COLUMN_SALARY = "SALARY";
    private static final String COLUMN_SIGNING_BONUS = "SIGNING_BONUS";
    private static final String COLUMN_YEARLY_BONUS = "YEARLY_BONUS";
    private static final String COLUMN_BENEFITS = "BENEFITS";
    private static final String COLUMN_LEAVE_TIME = "LEAVE_TIME";
    private static final String COLUMN_JOBOFFER = "JOBOFFER";

    private static final String createJobTableStatement = "CREATE TABLE " + JOB_TABLE + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, "
            + COLUMN_COMPANY + " TEXT, " + COLUMN_CITY + " TEXT, " + COLUMN_STATE + " TEXT, " +
            COLUMN_COL_INDEX + " INT, " + COLUMN_SALARY + " REAL, " + COLUMN_SIGNING_BONUS +
            " REAL, " + COLUMN_YEARLY_BONUS + " REAL, " + COLUMN_BENEFITS + " REAL, " +
            COLUMN_LEAVE_TIME + " INT, " + COLUMN_JOBOFFER + " INT)";

    //Weights Table:
    private static final String WEIGHTS_TABLE = "WEIGHTS_TABLE";
    private static final String COLUMN_SALARY_WEIGHT = "SALARY_WEIGHT";
    private static final String COLUMN_SIGNING_BONUS_WEIGHT = "SIGNING_BONUS_WEIGHT";
    private static final String COLUMN_YEARLY_BONUS_WEIGHT = "YEARLY_BONUS_WEIGHT";
    private static final String COLUMN_RETIREMENT_BENEFITS_WEIGHT = "RETIREMENT_BENEFITS_WEIGHT";
    private static final String COLUMN_LEAVE_TIME_WEIGHT = "LEAVE_TIME_WEIGHT";

    private static final String createWeightsTableStatement = "CREATE TABLE " + WEIGHTS_TABLE + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY, " +  COLUMN_SALARY_WEIGHT +
            " INT, " + COLUMN_SIGNING_BONUS_WEIGHT + " INT, " + COLUMN_YEARLY_BONUS_WEIGHT +
            " INT, " + COLUMN_RETIREMENT_BENEFITS_WEIGHT + " INT, " + COLUMN_LEAVE_TIME_WEIGHT +
            " INT)";

    private static final String insertInitialWeights = "INSERT INTO " + WEIGHTS_TABLE + " (" +
            COLUMN_ID + ", " +
            COLUMN_SALARY_WEIGHT + ", " + COLUMN_SIGNING_BONUS_WEIGHT + ", " +
            COLUMN_YEARLY_BONUS_WEIGHT + ", " + COLUMN_RETIREMENT_BENEFITS_WEIGHT + ", " +
            COLUMN_LEAVE_TIME_WEIGHT + ") VALUES (0, 1, 1, 1 ,1 ,1)";


    public DBHelper(@Nullable Context context) {
        super(context, "job.db", null, 1);
    }

    //Required method. Creates tables when database is created:
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createJobTableStatement);
        db.execSQL(createWeightsTableStatement);
        db.execSQL(insertInitialWeights);
    }

    //Required method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //Method to add one job to the Job table. No id# necessary because ID is autoincremented
    public boolean addJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, job.getTitle());
        cv.put(COLUMN_COMPANY, job.getCompany());
        cv.put(COLUMN_CITY, job.getCity());
        cv.put(COLUMN_STATE, job.getState());
        cv.put(COLUMN_COL_INDEX, job.getCOL());
        cv.put(COLUMN_SALARY, job.getSalary());
        cv.put(COLUMN_SIGNING_BONUS, job.getSignBonus());
        cv.put(COLUMN_YEARLY_BONUS, job.getYearBonus());
        cv.put(COLUMN_BENEFITS, job.getBenefits());
        cv.put(COLUMN_LEAVE_TIME, job.getLeaveTime());
        cv.put(COLUMN_JOBOFFER, job.isOffer());

        //nullColumnHack is optional, prevents inserting an empty row with no column names
        long insert = db.insert(JOB_TABLE, null, cv);
        db.close();
        if (insert < 0) {
            return false;
        }
        else return true;
    }

    //Method to update current job details in the Job table.
    public boolean updateJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, job.getTitle());
        cv.put(COLUMN_COMPANY, job.getCompany());
        cv.put(COLUMN_CITY, job.getCity());
        cv.put(COLUMN_STATE, job.getState());
        cv.put(COLUMN_COL_INDEX, job.getCOL());
        cv.put(COLUMN_SALARY, job.getSalary());
        cv.put(COLUMN_SIGNING_BONUS, job.getSignBonus());
        cv.put(COLUMN_YEARLY_BONUS, job.getYearBonus());
        cv.put(COLUMN_BENEFITS, job.getBenefits());
        cv.put(COLUMN_LEAVE_TIME, job.getLeaveTime());
        cv.put(COLUMN_JOBOFFER, job.isOffer());

        String[] args = new String[]{String.valueOf(job.getId())};
        int update = db.update(JOB_TABLE, cv, COLUMN_ID + " = ?", args);
        db.close();
        if (update < 0) {
            return false;
        } else {
            return true;
        }
    }

    //Method to update the weights:
    public boolean updateWeights(int salWeight, int signBonusWeight, int yearBonusWeight,
                                  int retireBenefitsWeight, int leaveTimeWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SALARY_WEIGHT, salWeight);
        cv.put(COLUMN_SIGNING_BONUS_WEIGHT,signBonusWeight);
        cv.put(COLUMN_YEARLY_BONUS_WEIGHT, yearBonusWeight);
        cv.put(COLUMN_RETIREMENT_BENEFITS_WEIGHT, retireBenefitsWeight);
        cv.put(COLUMN_LEAVE_TIME_WEIGHT, leaveTimeWeight);

        int update = db.update(WEIGHTS_TABLE, cv, COLUMN_ID + " = 0", null);
        db.close();
        if (update < 0) {
            return false;
        } else {
            return true;
        }
    }

    //Method to get the current weights:
    public List<Integer> getWeights() {
        List<Integer> returnList = new ArrayList();
        String queryString = "SELECT * FROM " + WEIGHTS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        //If there are results...
        if (cursor.moveToFirst()) {
            //int weightID = cursor.getInt(0);
            int salaryWeight = cursor.getInt(1);
            int signBonusWeight = cursor.getInt(2);
            int yearBonusWeight = cursor.getInt(3);
            int retireBenefitsWeight = cursor.getInt(4);
            int leaveTimeWeight = cursor.getInt(5);
            returnList.add(salaryWeight);
            returnList.add(signBonusWeight);
            returnList.add(yearBonusWeight);
            returnList.add(retireBenefitsWeight);
            returnList.add(leaveTimeWeight);
            }
        else {
            //Nothing in database
        }
        cursor.close();
        db.close();
        return returnList;
    }

    //Method to delete the records from the Job table:
    public boolean deleteJobTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + JOB_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        }

        else {
            cursor.close();
            db.close();
            return false;
        }
    }

    //Method which returns a list of all jobs in the Job table:
    public List<Job> getAllJobs() {
        List<Job> returnList = new ArrayList();
        String queryString = "SELECT * FROM " + JOB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        //If there are results...
        if (cursor.moveToFirst()) {
            //loop thru results and create new job for each result
            do {
                int jobID = cursor.getInt(0);
                String title = cursor.getString(1);
                String company = cursor.getString(2);
                String city = cursor.getString(3);
                String state = cursor.getString(4);
                int COL = cursor.getInt(5);
                float salary = cursor.getFloat(6);
                float signBonus = cursor.getFloat(7);
                float yearBonus = cursor.getFloat(8);
                float benefits = cursor.getFloat(9);
                int leaveTime = cursor.getInt(10);
                boolean isOffer = cursor.getInt(11) > 0;

                Job newJob = new Job(jobID, title, company, city, state, COL, salary, signBonus,
                yearBonus, benefits, leaveTime, isOffer);
                returnList.add(newJob);

            } while (cursor.moveToNext());
        }
        else {
            //Nothing in database
        }
        cursor.close();
        db.close();
        return returnList;
    }

    // Method to fetch the current job details.
    public Job getCurrentJob() {
        Job currentJob = null;
        String queryString = "SELECT * FROM " + JOB_TABLE +  " WHERE " + COLUMN_JOBOFFER + " = 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int jobID = cursor.getInt(0);
                String title = cursor.getString(1);
                String company = cursor.getString(2);
                String city = cursor.getString(3);
                String state = cursor.getString(4);
                int COL = cursor.getInt(5);
                float salary = cursor.getFloat(6);
                float signBonus = cursor.getFloat(7);
                float yearBonus = cursor.getFloat(8);
                float benefits = cursor.getFloat(9);
                int leaveTime = cursor.getInt(10);
                boolean isOffer = cursor.getInt(11) > 0;

                currentJob = new Job(jobID, title, company, city, state, COL, salary, signBonus,
                        yearBonus, benefits, leaveTime, isOffer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  currentJob;
    }

    // Method to return count of job offers.
    public int getJobOfferCount() {
        int offerCount = 0;
        String queryString = "SELECT COUNT(1) FROM " + JOB_TABLE +  " WHERE " + COLUMN_JOBOFFER + " > 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            offerCount = cursor.getInt(0);
            cursor.close();
            db.close();
        }
        return offerCount;
    }

    public boolean hasCurrentJob() {
        String queryString = "SELECT 1 FROM " + JOB_TABLE +  " WHERE " + COLUMN_JOBOFFER + " = 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }
}
