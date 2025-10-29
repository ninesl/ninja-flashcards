import axios from 'axios';
import store from '../store/index.js'; //import store to access data

export default{
    getOwnerUsername(id) {
        return axios.get(`/api/user/${id}`);
    },

    createDeck(deck){
        return axios.post(`/api/deck`, deck);
    },

    getAllDecks(){
        return axios.get('/api/deck/public');
    },

    getDeckByDeckId(deckId) {
        return axios.get(`/api/deck/${deckId}`)
    },

    updateDeck(deck){
        return axios.put(`/api/deck/${deck.deckId}`, deck);
    },

    deleteDeck(id){
        return axios.delete(`/api/deck/${id}`);
    },
    getMyDecks(id) {
        return axios.get(`/api/deck/myDecks/${id}`)
    },

    //Returns a num, 1 2 or 3 for score, 0 if no history
    //1 red, 2 yellow, 3 green
    getHistoryUserDeck(deckId) {
        const userId = store.state.user.id;
        return axios.get(`/api/deck/${deckId}/history/${userId}`);
    },
    //score is %, correct is optional number of correct answers
    updateUserDeckHistory(deckId, score, correct) {//returns response data
        const userId = store.state.user.id;
        //alert("PUT " + userId + " " + deckId + " " + score);
        const params = { score };
        if (correct !== undefined) params.correct = correct;
        return axios.put(`/api/deck/${deckId}/history/${userId}`,
        {}, { params });
    },
    createUserDeckHistory(deckId, score, correct) {//returns response data
        const userId = store.state.user.id;
        //alert("POST " + userId + " " + deckId + " " + score);
        const params = { score };
        if (correct !== undefined) params.correct = correct;
        return axios.post(`/api/deck/${deckId}/history/${userId}`,
        {}, { params });
    },

    getUserStudyReport(userId) {
        return axios.get(`/api/deck/report/${userId}`);
    }


}