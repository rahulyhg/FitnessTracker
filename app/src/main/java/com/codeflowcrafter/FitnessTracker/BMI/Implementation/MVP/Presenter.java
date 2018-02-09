package com.codeflowcrafter.FitnessTracker.BMI.Implementation.MVP;

import android.content.CursorLoader;
import android.net.Uri;

import com.codeflowcrafter.FitnessTracker.BMI.Implementation.Domain.BodyMassIndex;
import com.codeflowcrafter.FitnessTracker.Base.Domain.IEntityTranslator;
import com.codeflowcrafter.FitnessTracker.Base.MVP.Crud_Presenter;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerApplication;
import com.codeflowcrafter.FitnessTracker.FitnessTrackerContentProviders;
import com.codeflowcrafter.FitnessTracker.MapperInvocationDelegate;
import com.codeflowcrafter.LogManagement.Interfaces.IStaticLogEntryWrapper;
import com.codeflowcrafter.LogManagement.Priority;
import com.codeflowcrafter.LogManagement.Status;

/**
 * Created by enric on 09/02/2018.
 */

public class Presenter extends Crud_Presenter<BodyMassIndex, IRequests, IView>
        implements IRequests {
    private IView _view;
    private final static IStaticLogEntryWrapper _slc = FitnessTrackerApplication.GetInstance().CreateSLC();

    public Presenter(IView view, IEntityTranslator<BodyMassIndex> translator)
    {
        super(view,
                translator,
                new MapperInvocationDelegate(_slc));

        _slc.SetComponent("BMI");
        view.SetViewRequest(this);

        _view = view;
    }

    @Override
    public Uri GetContentUri() {
        return FitnessTrackerContentProviders
                .GetInstance()
                .GetBmiProvider()
                .GetContentUri();
    }

    @Override
    public int LoadEntitiesViaLoader(CursorLoader loader)
    {
        int size = super.LoadEntitiesViaLoader(loader);

        _slc
                .SetEvent(String.format("Loaded BMI count %d", size))
                .EmitLog(Priority.Info, Status.Success);

        return size;
    }

    @Override
    public void Prompt_AddEntry()
    {
        super.Prompt_AddEntry();
        _slc
                .SetEvent("Open BMI Entry")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void CancelEntry(){
        _slc
                .SetEvent("Cancel BMI entry window")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Add(BodyMassIndex entity)
    {
        super.Add(entity);
        _slc
                .SetEvent("BMI Added")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Update(BodyMassIndex entity)
    {
        super.Update(entity);
        _slc
                .SetEvent(String.format("Updated BMI id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_EditEntry(BodyMassIndex entity) {
        super.Prompt_EditEntry(entity);
        _slc
                .SetEvent("Open BMI editing")
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Delete(BodyMassIndex entity)
    {
        super.Delete(entity);
        _slc
                .SetEvent(String.format("Deleted BMI id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }

    @Override
    public void Prompt_Detail(BodyMassIndex entity)
    {
        super.Prompt_Detail(entity);
        _slc
                .SetEvent(String.format("Showing details of BMI id %s", entity.GetId()))
                .EmitLog(Priority.Info, Status.Success);
    }
}