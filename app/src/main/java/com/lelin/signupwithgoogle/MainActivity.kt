package com.lelin.signupwithgoogle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var gso:GoogleSignInOptions
    lateinit var signInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().build()

        signInClient=GoogleSignIn.getClient(this,gso)

        sign_in_button.setOnClickListener {
            singIn()
        }
    }


    private fun singIn(){
        val signinIntent=signInClient.signInIntent
        startActivityForResult(signinIntent,101)
    }

    private fun handleSignInResult(task:Task<GoogleSignInAccount>){
        try {
            val account=task.getResult(ApiException::class.java)
            updateUi(task.result!!)
        } catch (e:ApiException){
            Log.e("tag", "signInResult:failed code=" + e.statusCode)

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==101){
            val task=GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }


    private fun updateUi(account: GoogleSignInAccount){
       if (account !=null){
           Log.e("tag","email :  ${account.email}")
           Log.e("tag","id :  ${account.id}")
           Log.e("tag","photo :  ${account.photoUrl}")
           Log.e("tag","display name :  ${account.displayName}")
           Log.e("tag","family :  ${account.familyName}")
           Log.e("tag","given name :  ${account.givenName}")
       }
        else{
           Log.e("tag","null found")

       }
    }

    override fun onStart() {
        super.onStart()
        val account=GoogleSignIn.getLastSignedInAccount(this)
        if (account!=null){
             updateUi(account)

        }
    }
}
