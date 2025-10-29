import axios from 'axios';

export default{

    getAllCard(id){
        return axios.get(`/api/deck/${id}/card`);
    },

    addCard(card){
        return axios.post(`/api/deck/${card.deckId}/card`, card);
    },

    updateCard(card){
        return axios.put(`/api/deck/${card.deckId}/card/${card.cardId}`, card)
    },

    deleteCard(deckId, cardId){
        return axios.delete(`/api/deck/${deckId}/card/${cardId}`);
    }

}