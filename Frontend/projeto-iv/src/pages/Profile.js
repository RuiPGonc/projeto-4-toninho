import React from 'react'
import Sidebar from '../components/navbar/Sidebar'

function Profile() {
  return (
    <div className="Profile" id="profile-outer-container">
        <Sidebar pageWrapId={'profile-page-wrap'} outerContainerId={'profile-outer-container'} />
        <div className="page-wrap" id="profile-page-wrap">
          <h1>My Profile</h1>
      </div>
    </div>
  )
}

export default Profile
