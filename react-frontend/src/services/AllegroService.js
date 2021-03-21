import axios from 'axios'

const ALLEGRO_API_BASE_URL = "http://192.168.0.136:8080/allegro";

class AllegroService {

    getCategory() {
        return axios.get(ALLEGRO_API_BASE_URL + '/categories');
    }

    getCategoryByParentId(parentId) {
        return axios.get(ALLEGRO_API_BASE_URL + '/categories/' + parentId);
    }

    getOffers(params) {
        return axios.get(ALLEGRO_API_BASE_URL + '/offers', params);
    }
}

export default new AllegroService()