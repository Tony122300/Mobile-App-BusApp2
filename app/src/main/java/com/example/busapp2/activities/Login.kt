package com.example.busapp2.activities


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.busapp2.R
import com.example.busapp2.databinding.LoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber


class Login : AppCompatActivity(), View.OnClickListener {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth

    private lateinit var loginBinding: LoginBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = LoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)



        loginBinding.emailSignInButton.setOnClickListener(this)
        loginBinding.emailCreateAccountButton.setOnClickListener(this)
        loginBinding.signOutButton.setOnClickListener(this)
        loginBinding.verifyEmailButton.setOnClickListener(this)
        loginBinding.continueButton.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun createAccount(email: String, password: String) {
        Timber.d("createAccount:$email")
        if (!validateForm()) {
            return
        }

    auth.createUserWithEmailAndPassword(email, password)
    .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
            // Sign in success, update UI with the signed-in user's information
            Timber.d( "createUserWithEmail:success")
            val user = auth.currentUser
            updateUI(user)
        } else {
            // If sign in fails, display a message to the user.
            Timber.w( "createUserWithEmail:failure $task.exception")
            Toast.makeText(baseContext, "Authentication failed.",
                Toast.LENGTH_SHORT).show()
            updateUI(null)
        }

    }
    // [END create_user_with_email]
}
    private fun signIn(email: String, password: String) {
        Timber.d( "signIn:$email")
        if (!validateForm()) {
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d( "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.w( "signInWithEmail:failure $task.exception")
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
                if (!task.isSuccessful) {
                    loginBinding.status.setText(R.string.auth_failed)
                }

            }
        // [END sign_in_with_email]
    }

    private fun signOut() {
        auth.signOut()
        updateUI(null)
    }

    private fun continueApp() {
        val intent = Intent()
        intent.setClass(this,BusAppListActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            loginBinding.status.text = getString(R.string.emailpassword_status_fmt,
                user.email, user.isEmailVerified)
            loginBinding.detail.text = getString(R.string.firebase_status_fmt, user.uid)

            loginBinding.emailPasswordButtons.visibility = View.GONE
            loginBinding.emailPasswordFields.visibility = View.GONE
            loginBinding.signedInButtons.visibility = View.VISIBLE

            loginBinding.verifyEmailButton.isEnabled = !user.isEmailVerified
        } else {
            loginBinding.status.setText(R.string.signed_out)
            loginBinding.detail.text = null

            loginBinding.emailPasswordButtons.visibility = View.VISIBLE
            loginBinding.emailPasswordFields.visibility = View.VISIBLE
            loginBinding.signedInButtons.visibility = View.GONE
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = loginBinding.fieldEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            loginBinding.fieldEmail.error = "Required."
            valid = false
        } else {
            loginBinding.fieldEmail.error = null
        }

        val password = loginBinding.fieldPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            loginBinding.fieldPassword.error = "Required."
            valid = false
        } else {
            loginBinding.fieldPassword.error = null
        }

        return valid
    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.emailCreateAccountButton -> createAccount(loginBinding.fieldEmail.text.toString(), loginBinding.fieldPassword.text.toString())
            R.id.emailSignInButton -> signIn(loginBinding.fieldEmail.text.toString(), loginBinding.fieldPassword.text.toString())
            R.id.signOutButton -> signOut()
            R.id.continueButton -> continueApp()
            R.id.verifyEmailButton -> sendEmailVerification()
        }
    }

    private fun sendEmailVerification() {
        // Disable button
        loginBinding.verifyEmailButton.isEnabled = false

        // Send verification email
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) { task ->
                // [START_EXCLUDE]
                // Re-enable button
                loginBinding.verifyEmailButton.isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(baseContext,
                        "Verification email sent to ${user.email} ",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Timber.e( "sendEmailVerification $task.exception")
                    Toast.makeText(baseContext,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
                // [END_EXCLUDE]
            }
        // [END send_email_verification]
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}