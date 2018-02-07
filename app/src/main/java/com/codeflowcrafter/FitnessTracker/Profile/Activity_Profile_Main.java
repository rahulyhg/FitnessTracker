package com.codeflowcrafter.FitnessTracker.Profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codeflowcrafter.FitnessTracker.Base.Activity.Base_Activity_Main;
import com.codeflowcrafter.FitnessTracker.Base.Activity.DataContainer;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.Domain.Profile;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.MVP.Profile_IRequests;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.MVP.Profile_IView;
import com.codeflowcrafter.FitnessTracker.Profile.Implementation.MVP.Profile_Presenter;
import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Services.ActivityService;
import com.codeflowcrafter.FitnessTracker.TranslatorService;


public class Activity_Profile_Main extends Base_Activity_Main<
        Profile,
        Profile_IRequests,
        Activity_Profile_List_Item>
        implements Profile_IView
{
    private Profile_Presenter _presenter;

    private Profile_IRequests _viewRequest;
    public Profile_IRequests GetViewRequest(){return _viewRequest;}
    public void SetViewRequest(Profile_IRequests viewRequest){_viewRequest = viewRequest;}

    private Button _btnAdd;

    public Activity_Profile_Main()
    {
        super(
                R.layout.activity_profile_main,
                R.id.fragment_profileList
        );

        _presenter = new Profile_Presenter(
                this,
                TranslatorService.GetInstance().GetProfileTranslator()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public Activity_Profile_List_Item GetListItem(DataContainer<Profile> container)
    {
        return new Activity_Profile_List_Item(this, _viewRequest, container);
    }

    @Override
    public void AssociateViewToLocalVar()
    {
        _btnAdd = (Button)findViewById(R.id.btnAddProfile);
    }

    @Override
    public void SetViewHandlers()
    {
        _btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _viewRequest.Prompt_AddEntry();
            }
        });
    }

    public void OnPromptExecution_Detail(Profile entity){
        Activity_Profile_Dialog_ReadAddEdit.Show(
                getFragmentManager(), _viewRequest,
                Activity_Profile_Dialog_ReadAddEdit.ACTION_READ, entity);
    }

    public void OnPromptExecution_AddEntry(){
        Activity_Profile_Dialog_ReadAddEdit.Show(
                getFragmentManager(), _viewRequest,
                Activity_Profile_Dialog_ReadAddEdit.ACTION_ADD, null);
    }

    public void OnPromptExecution_EditEntry(Profile entity){
        Activity_Profile_Dialog_ReadAddEdit.Show(
                getFragmentManager(), _viewRequest,
                Activity_Profile_Dialog_ReadAddEdit.ACTION_EDIT, entity);
    }

    public void OnPromptExecution_DeleteEntry(final Profile entity){
        AlertDialog.Builder verify = ActivityService.CreateDeleteAlertDialog(this);

        verify.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg) {
                        _viewRequest.Delete(entity);
                        String message = "'" + entity.GetName() + "' profile deleted";
                        Toast
                                .makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                                .show();
                    }
                }
        );

        verify.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg) {
                        //No action takes
                    }
                }
        );

        verify.show();
    }
}
