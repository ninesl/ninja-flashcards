<template>
  <div class="banner-conatiner">
        <div class="banner-text-block">
            <div class="last-title-btn"> 
                <h2 class="fr-last-title">Welcome to Ninja Flashcards</h2>
            </div>
            <div class="banner-first-row">
                <div class="fr-column">
                    <h3 class="fr-content">
                        <p>Master any subject with flashcard decks tailored to your learning style. Create, organize, and study your way to better understanding.</p>
                        
                        <p>Build custom flashcard collections and choose who can access them. Keep your study materials private, share them with select people, or contribute to the community knowledge base.</p>
                        
                        <p>See your progress in real-time with intelligent performance tracking. Visual indicators show your mastery level across different topics, helping you focus your study time where it matters most.</p>
                        
                        <p>Access your flashcards anywhere with our mobile-friendly design. Your study progress syncs across all devices, so you can learn on the go.</p>
                    </h3>
                </div>
                <div class="fr-image" id="fr-img">
                    <img id="desk-notebook" src="../assets/pexels-pixabay-459791.jpg" alt="Desk with notebook" width="100%" height="auto">
                </div> 
            </div>

        </div>
    </div>
</template>

<script>

export default {
    name: 'the-banner',
    methods: {
        goToRegister() {
            this.$router.push({name: 'register'});
        }
    }
}
</script>

<style scoped>
p {
    font-size: 18px;
    text-align: justify;
}
.external-link {
    color: blue;
}

#desk-notebook {
    border-radius: 10px;
    box-shadow: 2px 3px 5px;
    margin-top: 10px;
}
.banner-f-title {
    text-align: center;
    text-shadow: 1px 1px 1px rgba(57, 158, 57, 0.76);
    font-size: 2em;

}

.banner-first-row {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    padding: 20px;
    overflow: hidden;
}

.fr-title {
    font-size: 2em;
}
.fr-column {
    width: 50%;
}
.fr-image {
    margin-left: 5%;
    width: 50%;
}

.last-title-btn {
    display: flex;
    justify-content: center;
    align-items: baseline;
    margin: auto;
}

.fr-last-title{
    text-align: center;
    text-shadow: 3px 3px 5px rgba(57, 158, 57, 0.76);
    font-size: 2em;
}

#last-btn-shaped{
    border-radius: 16px;
    padding: 12px 24px;
    margin-left: 10px;
}

@media only screen and (max-width: 1300px) {
    .last-title-btn {
        flex-direction: column;
        align-items: center;
    }
    .banner-first-row {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
    .fr-column, .fr-image {
        margin: 10px;
        width: 100%;
        padding: 0;
    }
    .fr-title {
        margin: auto;
        text-align: center;
    }

}
/* 
@media only screen and (max-width: 590px) {

    .fr-content {
        max-width: 60%;
    }

}

@media only screen and (max-width: 500px) {

    .fr-content {
        max-width: 50%;
    }

}

@media only screen and (max-width: 400px) {

    .fr-content {
        max-width: 40%;
    }

} */

</style>
