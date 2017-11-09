package cn.enjoytoday.rimedoj.fragments


import android.net.Uri
import android.support.v4.app.Fragment

/**
 * @date 17-11-9.
 * @className OnFragmentInteractionListener
 * @serial 1.0.0
 */


/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 *
 *
 * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
 */
interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    fun onFragmentInteraction(uri: Uri,fragment: Fragment)
}