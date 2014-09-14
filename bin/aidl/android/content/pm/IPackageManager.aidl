/*
**
** Copyright 2007, The Android Open Source Project
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**     http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*/

package android.content.pm;

import android.content.pm.IPackageMoveObserver;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver;
/**
 *  See {@link PackageManager} for documentation on most of the APIs
 *  here.
 * 
 *  {@hide}
 */
interface IPackageManager {
    void movePackage(String packageName, IPackageMoveObserver observer, int flags);
    void deleteApplicationCacheFiles(in String packageName, IPackageDataObserver observer);
    /**
     * As per {@link android.content.pm.PackageManager#setComponentEnabledSetting}.
     */
    void setComponentEnabledSetting(in ComponentName componentName,
            in int newState, in int flags, int userId);
    /**
     * As per {@link android.content.pm.PackageManager#getComponentEnabledSetting}.
     */
    int getComponentEnabledSetting(in ComponentName componentName, int userId);
     /**
     * As per {@link android.content.pm.PackageManager#setApplicationEnabledSetting}.
     */
    void setApplicationEnabledSetting(in String packageName, in int newState, int flags,
            int userId, String callingPackage);
    
    /**
     * As per {@link android.content.pm.PackageManager#getApplicationEnabledSetting}.
     */
    int getApplicationEnabledSetting(in String packageName, int userId);
        /**
     * Delete a package for a specific user.
     *
     * @param packageName The fully qualified name of the package to delete.
     * @param observer a callback to use to notify when the package deletion in finished.
     * @param userId the id of the user for whom to delete the package
     * @param flags - possible values: {@link #DONT_DELETE_DATA}
     */
    void deletePackageAsUser(in String packageName, IPackageDeleteObserver observer,
            int userId, int flags);
    
}