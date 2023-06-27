package com.example.gatewaygetaways.activity

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.gatewaygetaways.R
import com.example.gatewaygetaways.databinding.ActivityDashboardBinding
import com.example.gatewaygetaways.fragment.BookingFragment
import com.example.gatewaygetaways.fragment.ExploreFragment
import com.example.gatewaygetaways.fragment.ProfileFragment
import com.example.gatewaygetaways.fragment.WishlistFragment
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class DashboardActivity : AppCompatActivity() {
    lateinit var dashboardBinding: ActivityDashboardBinding
    lateinit var storageReference: StorageReference
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var googleSignInClient: GoogleSignInClient

    private var mInterstitialAd: InterstitialAd? = null
    lateinit var adRequest : AdRequest
    private final var TAG = "DashboardActivity"
    var buttononclick = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(dashboardBinding.root)

        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        storageReference = storage.getReference()

        initview()
        interstitailads()
    }

    private fun interstitailads() {
        adRequest = AdRequest.Builder().build()
        Loadads()

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
                Loadads()

            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
    }

    private fun Loadads() {
        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun initview() {
        firebaseDatabase = FirebaseDatabase.getInstance()


        dashboardBinding.drawermenu.setOnClickListener {
            dashboardBinding.navigationdrawer.openDrawer(GravityCompat.START)
        }

        dashboardBinding.imgsearchbtn.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)

            startActivity(intent)
        }

        dashboardBinding.drawerhome.setOnClickListener {
            dashboardBinding.navigationdrawer.closeDrawer(GravityCompat.START)
            Toast.makeText(this, "you are already in category", Toast.LENGTH_SHORT).show()
        }

        dashboardBinding.privacypolicy.setOnClickListener {
            var url =
                "https://akshaypatel4120.blogspot.com/2023/04/summary-of-changes-weve-updated-how-we.html"
            var linkprivacy = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(linkprivacy)
        }

        dashboardBinding.ratetheapp.setOnClickListener {
            var url =
                "https://play.google.com/store/apps/details?id=com.tripadvisor.tripadvisor&hl=en-IN"
            var linkrate = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(linkrate)
        }

        dashboardBinding.feedback.setOnClickListener {
            var url = "https://www.tripadvisor.in/UserReview"
            var linkrate = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(linkrate)
        }

        dashboardBinding.share.setOnClickListener {
            var url = "https://www.whatsapp.com"
            var linkshare = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(linkshare)
        }

        dashboardBinding.termofservice.setOnClickListener {
            var url =
                "https://www.blogger.com/blog/post/edit/preview/4257247768763819674/2835630061634682603"
            var linkshare = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(linkshare)
        }

        class DrawerItemClickListener : OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectItem(position)
            }

            private fun selectItem(position: Int) {

//                Fragment fragment = null;
//
//                switch (position) {
//                    case 0:
//                    fragment = new ConnectFragment();
//                    break;
//                    case 1:
//                    fragment = new FixturesFragment();
//                    break;
//                    case 2:
//                    fragment = new TableFragment();
//                    break;
//
//                    default:
//                    break;
            }
        }


        dashboardBinding.btnlogout.setOnClickListener {
            var sharedPreferences = getSharedPreferences("MySharePref", MODE_PRIVATE)
            var myEdit: SharedPreferences.Editor = sharedPreferences.edit()
            myEdit.remove("isLogin")
            myEdit.commit()
            var intent = Intent(this, TypesOfLoginActivity::class.java)
            startActivity(intent)

        }

        // google logout
        // Initialize sign in client
        googleSignInClient =
            GoogleSignIn.getClient(this@DashboardActivity, GoogleSignInOptions.DEFAULT_SIGN_IN)
        dashboardBinding.txtlogoutGoogle.setOnClickListener {
            // Sign out from google
            googleSignInClient.signOut().addOnCompleteListener { task ->
                // Check condition
                if (task.isSuccessful) {
                    // When task is successful sign out from firebase
                    auth.signOut()
                    // Display Toast
                    Toast.makeText(applicationContext, "Logout successful", Toast.LENGTH_SHORT)
                        .show()
                    // Finish activity
                    finish()
                }
            }
        }

        val pack: PackageManager = packageManager
        val info: PackageInfo = pack.getPackageInfo(packageName, 0)
        val version: String = info.versionName
        dashboardBinding.txtversion.text = version

        loadFragment(ExploreFragment())
        dashboardBinding.bottomNavigationView.setOnItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener,
            NavigationBarView.OnItemSelectedListener {

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                lateinit var fragment: Fragment
                when (item.itemId) {
                    R.id.explore_bottom -> {
                        fragment = ExploreFragment()
                        loadFragment(fragment)
                    }

                    R.id.booking_bottom -> {
                        fragment = BookingFragment()
                        loadFragment(fragment)
                    }

                    R.id.wishlist_bottom -> {
//                        interstitailads()
                        if(buttononclick <= 2){
                           buttononclick++
                        }else {
                            buttononclick = 0
                            interstitailads()
                            mInterstitialAd?.show(this@DashboardActivity)
                        }

//                        if (mInterstitialAd != null) {
//                            mInterstitialAd?.show(this@DashboardActivity)
//                        } else {
//                            Log.d("TAG", "The interstitial ad wasn't ready yet.")
//                        }

                        fragment = WishlistFragment()
                        loadFragment(fragment)
                    }

                    R.id.profile_bottom -> {
                        fragment = ProfileFragment()
                        loadFragment(fragment)
                    }
                }
                return true
            }
        })

    }

    private fun loadFragment(frag: Fragment) {

        val fm: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, frag)
        fragmentTransaction.commit()
    }


}
