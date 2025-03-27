import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../Security/AuthContext";

export default function LoginComponent() {
  const [username, setUsername] = useState("Madhav");
  const [password, setPassword] = useState("123456");
  const [showErrorMessage, setShowErrorMessage] = useState(false);

  const authContext = useAuth();
  const navigate = useNavigate();

  function handleUsernameChange(event) {
    setUsername(event.target.value);
  }

  function handlePasswordChange(event) {
    setPassword(event.target.value);
  }

  async function handleSubmit(event) {
    event.preventDefault(); 

    console.log("Username:", username, "Password:", password);

    try {
      const success = await authContext.login(username, password);
      if (success) {
        // navigate(`/welcome/${username}`);
        console.log("Yes Sucessful")
      } else {
        setShowErrorMessage(true);
      }
    } catch (error) {
      setShowErrorMessage(true);
    }
  }

  return (
    <div className="Login">
      <h1>Time To Login!</h1>
      {showErrorMessage && (
        <div className="ErrorMessage">Authentication Failed, please retry</div>
      )}
      <form className="LoginForm" onSubmit={handleSubmit}>
        <div>
          <label>User Name</label>
          <input type="text" value={username} onChange={handleUsernameChange} />
        </div>
        <div>
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={handlePasswordChange}
          />
        </div>
        <div>
          <button type="submit">Login</button>
        </div>
      </form>
    </div>
  );
}
