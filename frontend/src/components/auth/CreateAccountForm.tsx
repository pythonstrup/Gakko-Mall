import React, { useContext, useRef } from 'react';
import { Button, Container, Form, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import AuthContext from '../../store/auth-context';

const CreateAccountForm = () => {

    let navigate = useNavigate();
    const authContext = useContext(AuthContext);
    const emailInputRef = useRef<HTMLInputElement>(null);
    const passwordInputRef = useRef<HTMLInputElement>(null);
    const nicknameInputRef = useRef<HTMLInputElement>(null);

    const submitHandler = (event: React.FormEvent) => {
        event.preventDefault();

        const enteredEmail = emailInputRef.current!.value;
        const enteredPassword = passwordInputRef.current!.value;
        const enteredNickname = nicknameInputRef.current!.value;

        authContext.signup(enteredEmail, enteredPassword, enteredNickname);

        if(authContext.isSuccess) {
            return navigate("/", {replace: true});
        }
    }

    return (
        <div>
            <Container>
                <h1>회원가입 페이지</h1>
                <Form onSubmit={submitHandler} className="panel">
                    <Form.Group as={Row} className="mb-3">
                        <Form.Label htmlFor="email">Your email</Form.Label>
                        <Form.Control type="email" id="id" name="email" required ref={emailInputRef}/>
                    </Form.Group>
                    <Form.Group as={Row} className="mb-3" controlId="formPlaintextPassword">
                        <Form.Label htmlFor="password">Your password</Form.Label>
                        <Form.Control type='password' id='password' name="password" required ref={passwordInputRef}/>
                    </Form.Group>
                    <Form.Group as={Row} className="mb-3">
                        <Form.Label htmlFor='nickname'>NickName</Form.Label>
                        <Form.Control type="text" id="nickname" name="nickname" required ref={passwordInputRef}/>
                    </Form.Group>
                    <div>
                        <Button type='submit'>
                            Submit
                        </Button>
                    </div>
                </Form>
            </Container>
        </div>
    );
};

export default CreateAccountForm;