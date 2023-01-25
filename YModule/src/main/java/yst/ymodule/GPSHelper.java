/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yst.ymodule;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;

/**
 * @author MrHadi
 */
public class GPSHelper {

    //	private static final long LocationRefreshInterval = 4000;
//	LocationManager locationManager;
//	LocationListener locationListener;
    Context context;

    public GPSHelper(Context con) {
        context = con;
//		init_GPS();
    }

    private boolean canToggleGPS() {
        try {
            PackageManager pacman = context.getPackageManager();
            PackageInfo pacInfo = null;

            try {
                pacInfo = pacman.getPackageInfo("com.android.settings",
                        PackageManager.GET_RECEIVERS);
            } catch (PackageManager.NameNotFoundException e) {
                return false; // package not found
            }

            if (pacInfo != null) {
                for (ActivityInfo actInfo : pacInfo.receivers) {
                    // test if recevier is exported. if so, we can toggle GPS.
                    if (actInfo.name
                            .equals("com.android.settings.widget.SettingsAppWidgetProvider")
                            && actInfo.exported) {
                        return true;
                    }
                }
            }

        } catch (Exception e) {
        }
        return false; // default

    }

    public void turnGPSOn() {
        try {
            String provider = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (!provider.contains("gps")) { // if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings",
                        "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                context.sendBroadcast(poke);
            }
        } catch (Exception e) {
        }
    }

    public void turnGPSOff() {
        String provider = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.contains("gps")) { // if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            context.sendBroadcast(poke);
        }
        LogCatHelper.ShowDebugLog(null, "GPS Turned Off");
    }

    public boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean ret = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return ret;
    }

//	private void init_GPS() {
//
//		// Acquire a reference to the system Location Manager
//		locationManager = (LocationManager) context
//				.getSystemService(Context.LOCATION_SERVICE);
//		if (!ValueKeeper.UseBTSOnly) {
//			if (!locationManager
//					.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//				try {
//					ValueKeeper.ShowDebugLog(null, "GPS Not Enabled...");
//					if (canToggleGPS()) {
//						ValueKeeper.ShowDebugLog(null, "Can Toggle GPS...");
//						turnGPSOn();
//						ValueKeeper.ShowDebugLog(null, "GPS Turned On...");
//					} else {
//						ValueKeeper.ShowDebugLog(null, "GPS Not Enabled2");
//						Intent I = new Intent(
//								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//						I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						context.startActivity(I);
//					}
//				} catch (Exception e) {
//				}
//			}
//		}
//		//
//		locationListener = new LocationListener() {
//			public void onLocationChanged(Location lctn) {
//				ValueKeeper.ShowDebugLog(
//						null,
//						"New GPS Location: " + lctn.getLatitude() + ", "
//								+ lctn.getLongitude());
//
//
//				try {
//					if (Bug_GPS.txtLatt != null) {
//						Bug_GPS.txtLatt.setText(lctn.getLatitude() + "");
//					}
//					if (Bug_GPS.txtLongt != null) {
//						Bug_GPS.txtLongt
//								.setText(lctn.getLongitude() + "");
//					}
//				} catch (Exception e) {
//				}
//				try {
//					if (Action.txtLatt != null) {
//						Action.txtLatt.setText(lctn.getLatitude() + "");
//					}
//					if (Action.txtLongt != null) {
//						Action.txtLongt
//								.setText(lctn.getLongitude() + "");
//					}
//				} catch (Exception e) {
//				}
//			}
//
//			public void onStatusChanged(String string, int i, Bundle bundle) {
//			}
//
//			public void onProviderEnabled(String string) {
//			}
//
//			public void onProviderDisabled(String string) {
//			}
//		};
//		if (!ValueKeeper.UseBTSOnly) {
//			locationManager.requestLocationUpdates(
//					LocationManager.GPS_PROVIDER, LocationRefreshInterval, 0,
//					locationListener);
//		} else {
//			locationManager.requestLocationUpdates(
//					LocationManager.NETWORK_PROVIDER, LocationRefreshInterval,
//					0, locationListener);
//		}
//	}

//	public void StopGPSListener() {
//		try {
//			if (locationManager != null) {
//				locationManager.removeUpdates(locationListener);
//			}
//			ValueKeeper.ShowDebugLog(null, "GPS Listener Stopped");
//		} catch (Exception e) {
//			Log.e(ValueKeeper.tag,
//					"Error Stopping GPS Listener: " + e.getMessage());
//		}
//	}
//
//	public void StartGPSListener() {
//		try {
//			if (!ValueKeeper.UseBTSOnly) {
//				locationManager.requestLocationUpdates(
//						LocationManager.GPS_PROVIDER, LocationRefreshInterval, 0,
//						locationListener);
//			} else {
//				locationManager.requestLocationUpdates(
//						LocationManager.NETWORK_PROVIDER, LocationRefreshInterval,
//						0, locationListener);
//			}
//			ValueKeeper.ShowDebugLog(null, "GPS Listener Started");
//		} catch (Exception e) {
//			Log.e(ValueKeeper.tag,
//					"Error Starting GPS Listener: " + e.getMessage());
//		}
//	}
}
