import React from 'react'
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom' 
import AuthProvider from '../Security/AuthContext';
import LoginComponent from './loginComponent';
import HeaderComponent from './HeaderComponent';

function RawBytes() {

    function AuthenticatedRoute({children}){
        const authContext = useAuth();
        if(authContext.isAuthenticated){
            return children;
        }
        return <Navigate to="/login" />
    }
  return (
    <div className='RawBytes'>
        <AuthProvider>
            <BrowserRouter>
                <HeaderComponent/>
                <Routes>
                    <Route path='/login' element={<LoginComponent />} />
                    {/* <Route path='/' element={HomeComponent} />
                    <Route path = '/register' element={<RegisterComponent />} />
                    <Route path='/welcome' element={
                        <AuthenticatedRoute>
                            <WelcomeComponent />
                        </AuthenticatedRoute>} />
                    <Route path='/explore' element={
                        <AuthenticatedRoute>
                            <ExploreComponent />
                        </AuthenticatedRoute>} />
                    <Route path='/payment' element={
                        <AuthenticatedRoute>
                            <LogoutComponent />
                        </AuthenticatedRoute>} />  
                    <Route path='/logout' element={
                        <AuthenticatedRoute>
                            <LogoutComponent />
                        </AuthenticatedRoute>} />    */}
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    </div>
  )
}

export default RawBytes