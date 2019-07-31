/*
 * Copyright (C) 2012 The CyanogenMod Project
 * Copyright (C) 2017 The LineageOS Project
 * Copyright (C) 2019 The AOSP-CAF-MSM8916 Project
 *
 * * Licensed under the GNU GPLv2 license
 *
 * The text of the license can be found in the LICENSE file
 * or at https://www.gnu.org/licenses/gpl-2.0.txt
 */
package com.teamone.updater.service;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.teamone.updater.utils.UpdateChecker;
import com.teamone.updater.utils.UpdaterCheckerResult;

public class UpdateCheckService extends JobService {

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        new UpdateChecker(this, new UpdaterCheckerResult() {
            @Override
            public void onResult(boolean result) {
                jobFinished(jobParameters, false);
                UpdateChecker.scheduleUpdateService(UpdateCheckService.this);
            }
        }).check();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        UpdateChecker.cancelAllRequests(this);
        UpdateChecker.scheduleUpdateService(this);
        return false;
    }

}
