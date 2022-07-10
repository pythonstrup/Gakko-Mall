import React, { useContext, useRef, useState } from 'react';
import { Button, Container, Form, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import AuthContext from '../../store/auth-context';

const AuthForm = () => {

    const emailInputRef = useRef<HTMLInputElement>(null);
    const passwordInputRef = useRef<HTMLInputElement>(null);

    let navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(false);
    const authContext = useContext(AuthContext);

    const submitHandler = async (event: React.FormEvent) => {
        event.preventDefault();

        const enteredEmail = emailInputRef.current!.value;
        const enteredPassword = passwordInputRef.current!.value;

        setIsLoading(true);
        authContext.login(enteredEmail, enteredPassword);
        setIsLoading(false);

        if(authContext.isSuccess) {
            navigate("/", {replace: true});
        }
    }

    return (
        <div>
            <Container>
                <h1>로그인 페이지</h1>
                <Form onSubmit={submitHandler} className="panel">
                    <Form.Group as={Row} className="mb-3">
                        <Form.Label htmlFor="email">Your email</Form.Label>
                        <Form.Control type="email" id="id" name="email" required ref={emailInputRef}/>
                    </Form.Group>
                    <Form.Group as={Row} className="mb-3" controlId="formPlaintextPassword">
                        <Form.Label htmlFor="password">Your password</Form.Label>
                        <Form.Control type='password' id='password' name="password" required ref={passwordInputRef}/>
                    </Form.Group>
                    <div>
                        <Button type='submit' variant="primary">Login</Button>
                        {isLoading && <p>Loading</p>}
                    </div>
                </Form>
            </Container>
        </div>
    );
};

export default AuthForm;