import axios from 'axios'

const TARGET_API_BASE_URL = "http://192.168.0.136:8080/target";

class TargetService {

    getTargets() {
        return axios.get(TARGET_API_BASE_URL);
    }

    createTarget(target) {
        return axios.post(TARGET_API_BASE_URL, target);
    }

    getTarget(targetId) {
        return axios.get(TARGET_API_BASE_URL + '/' + targetId);
    }

    updateTarget(targetId, target) {
        return axios.put(TARGET_API_BASE_URL + '/' + targetId, target);
    }

    deleteTarget(targetId) {
        return axios.delete(TARGET_API_BASE_URL + '/' + targetId);
    }
}

export default new TargetService()