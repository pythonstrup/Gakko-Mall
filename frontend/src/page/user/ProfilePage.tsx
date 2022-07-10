import React, { Fragment } from 'react';
import ChangePassword from '../../components/profile/ChangePassword';
import ChangeUsername from '../../components/profile/ChangeUsername';

const ProfilePage = () => {
    return (
        <div>
            <Fragment>
                <ChangeUsername />
                <ChangePassword />
            </Fragment>
        </div>
    );
};

export default ProfilePage;