import axios from 'axios'

const BLACKLIST_USER_API_BASE_URL = "http://localhost:8080/blacklist_user";

class BlacklistUserService {

    getBlacklistUsers() {
        return axios.get(BLACKLIST_USER_API_BASE_URL);
    }

    createBlacklistUser(blacklistUser) {
        return axios.post(BLACKLIST_USER_API_BASE_URL, blacklistUser);
    }

    getBlacklistUser(blacklistUserId) {
        return axios.get(BLACKLIST_USER_API_BASE_URL + '/' + blacklistUserId);
    }

    deleteBlacklistUser(blacklistUserId) {
        return axios.delete(BLACKLIST_USER_API_BASE_URL + '/' + blacklistUserId);
    }
}

export default new BlacklistUserService()