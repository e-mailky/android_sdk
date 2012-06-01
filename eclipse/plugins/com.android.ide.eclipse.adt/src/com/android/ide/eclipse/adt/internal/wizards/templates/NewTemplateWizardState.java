/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.eclipse.org/org/documents/epl-v10.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.ide.eclipse.adt.internal.wizards.templates;

import static com.android.ide.eclipse.adt.internal.wizards.templates.NewTemplateWizard.BLANK_ACTIVITY;

import com.android.annotations.NonNull;

import org.eclipse.core.resources.IProject;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Value object which holds the current state of the wizard pages for the
 * {@link NewTemplateWizard}
 */
public class NewTemplateWizardState {
    /** Name of the template being created */
    private String mTemplateName = BLANK_ACTIVITY;

    /** Template handler responsible for instantiating templates and reading resources */
    private TemplateHandler mTemplateHandler;

    /** Configured parameters, by id */
    public final Map<String, Object> parameters = new HashMap<String, Object>();

    /** Configured defaults for the parameters, by id */
    public final Map<String, String> defaults = new HashMap<String, String>();

    /** Ids for parameters which should be hidden (because the client wizard already
     * has information for these parameters) */
    public final Set<String> hidden = new HashSet<String>();

    /**
     * The chosen project (which may be null if the wizard page is being
     * embedded in the new project wizard)
     */
    public IProject project;

    /** Name of the template being created */
    private File mTemplateLocation;

    /**
     * Create a new state object for use by the {@link NewTemplatePage}
     */
    public NewTemplateWizardState() {
    }

    @NonNull
    String getTemplateName() {
        return mTemplateName;
    }

    /**
     * Sets the new template name to use
     *
     * @param templateName the name of the template to use
     */
    void setTemplateName(@NonNull String templateName) {
        if (!templateName.equals(mTemplateName)) {
            mTemplateName = templateName;
            mTemplateLocation = null;
            mTemplateHandler = null;
        }
    }

    @NonNull
    TemplateHandler getTemplateHandler() {
        if (mTemplateHandler == null) {
            File inputPath;
            if (mTemplateLocation != null) {
                inputPath = mTemplateLocation;
            } else {
                inputPath = new File(TemplateHandler.getTemplatePath(mTemplateName));
            }
            mTemplateHandler = TemplateHandler.createFromPath(inputPath);
        }

        return mTemplateHandler;
    }

    // For template development/testing only
    void setTemplateLocation(File file) {
        if (!file.equals(mTemplateLocation)) {
            mTemplateLocation = file;
            mTemplateName = null;
            mTemplateHandler = null;
        }
    }
}