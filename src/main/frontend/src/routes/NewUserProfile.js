import axios from 'axios';

function NewUserProfile() {
  const handleSubmit = (event) => {
    event.preventDefault();
    axios.post('http://localhost:8081/api/v1/user-profile').then((data) => {
      console.log(data);
    });
  };
  return (
    <div>
      <form onSubmit={handleSubmit}>
        <h3> Add new user profile</h3>
        <ul>
          <li>
            <label>
              Name:
              <input type='text'></input>
            </label>
          </li>
          <li>
            <label>
              Profile Image:
              <input type='file' accept='image/*'></input>
            </label>
          </li>
        </ul>
        <button>Add</button>
      </form>
    </div>
  );
}

export default NewUserProfile;
