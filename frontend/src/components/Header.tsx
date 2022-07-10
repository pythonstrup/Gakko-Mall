import React, { useContext, useEffect, useState } from 'react';
import { Button, Container, Form, FormControl, Nav, Navbar } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import AuthContext from '../store/auth-context';

const Header = () => {

    const authContext = useContext(AuthContext);
    const [nickname, setNickname] = useState("");
    let isLogin = authContext.isLoggedIn;
    let isGet = authContext.isGetSuccess;

    const callback = (str: string) => {
        setNickname(str);
    }

    useEffect(() => {
        if (isGet) {
            console.log("get start");
            callback(authContext.userObj.nickname);
        }
    }, [isGet]);

    const toggleLogoutHandler = () => {
        authContext.logout();
    }

    // 로그인과 회원가입은 로그인되지 않았을 때 조회가능
    // 프로필 편집과 로그아웃은 로그인 상태여야 볼 수 있다.
    return (
    <>
        <Navbar bg="dark" variant="dark">
            <Container>
                <Link to="/" className='navbar-brand'>Home</Link>
                <Nav className="me-auto">
                    {!isLogin && <Link to="/signup" className='nav-link'>회원가입</Link>}
                    {!isLogin && <Link to="/login" className='nav-link'>로그인</Link>}
                    {isLogin && <Link to="/profile" className='nav-link'>프로필 편집</Link>}
                    {isLogin && <button onClick={toggleLogoutHandler} className='nav-link'>로그아웃</button>}
                </Nav>
                <Form className="d-flex">
                <FormControl
                    type="search"
                    placeholder="Search"
                    className="me-2"
                    aria-label="Search"
                    />
                <Button variant="outline-success">Search</Button>
                </Form>
            </Container>
            <br/>
        </Navbar>
    </>
    );
};

export default Header;