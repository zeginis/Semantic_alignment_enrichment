import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api`,
  timeout: 1000
});


export default {
    getUser(userId) {
        return AXIOS.get(`/user/` + userId);
    },
    createUser(dlabel,dlang) {
    	return AXIOS.post(`/dataset`,{label:dlabel, language:dlang});
    }
   
}


