import React, { Fragment } from 'react';
import Header from './Header';

type Props = {
    children?: React.ReactNode
}

const Layout: React.FC<Props> = (props) => {
    return (
        <Fragment>
            <Header/>
            <main>{props.children}</main>
        </Fragment>
    );
};

export default Layout;