import { GigJSON } from '../login-view/model/GigJSON';
import { Component, OnInit, ElementRef, ViewChild, Input } from '@angular/core';
import $ from 'jquery';
import { GigViewModel } from '../gig-table/model/GigViewModel';
import { IdObject } from '../core/model/IdObject';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { BubbleService } from '../_services/bubble.service';
import { DialogService } from '../_services/dialog.service';
import { LoginService } from '../_services/login.service';
import { GetAllGenresCommand } from './command/get-all-genres.command';
import { MinifiedJSON } from './model/minified.json';
import { GenreViewModel } from './model/genre.model';
import { ArtistViewModel } from './model/artist..model';
import { CityViewModel } from './model/city.model';
import { GetAllCitiesCommand } from './command/get-all-cities.command';
import { GetAllArtistsCommand } from './command/get-all-artists.command';
import { EditCityCommand } from './command/edit-city.command';
import { SaveNewCityCommand } from './command/save-new-city.command';
import { DeleteArtistCommand } from './command/delete-artist.command';
import { CreateArtistCommand } from './command/create-artist.command';
import { DialogButton } from '../core/dialog/model/DialogButton';
import { DeleteGigCommand } from './command/delete-gig.command';
import { NewCityCommand } from './command/new-city.command';

let $EditGenre: GenreViewModel;
let $EditCity: CityViewModel;
let $EditArtist: ArtistViewModel;

const notNullOrEmpty = value => value && value != null && value.trim().length > 0;
const handleDone = (model: IdObject) => {
    if (model != null) {
        model.disabled = false;
    }
};
const setSelectedIdObject = (model: IdObject, current: IdObject) => {
    model.editSelected = model.id === current.id;
};
const handleSingleSelection = (sourceList: IdObject[], current: IdObject) => {
    sourceList.forEach(item => setSelectedIdObject(item, current));
};

@Component({
    // tslint:disable-next-line:component-selector
    selector: 'gig-edit-form',
    templateUrl: './gig-edit.component.html',
    styleUrls: ['./gig-edit.component.css'],
    providers: [BubbleService, DialogService, LoginService]
})
export class GigEditComponent implements OnInit {

    @Input() date: Date;
    @Input() time: Date;
    @Input() genres: Array<GenreViewModel>;
    @Input() artists: Array<ArtistViewModel>;
    @Input() cities: Array<CityViewModel>;
    @Input() gigModel: GigViewModel;
    buttonsDisabled: boolean;

    constructor(
        private readonly bubbleService: BubbleService,
        private readonly dialogService: DialogService,
        private readonly loginService: LoginService) {
        this.onPrepareNewGenreClicked();
        this.onCitySaveAddButtonClicked();
        this.onPrepareNewArtistClicked();
        this.genres = new Array<GenreViewModel>();
        this.artists = new Array<ArtistViewModel>();
        this.cities = new Array<CityViewModel>();
        this.buttonsDisabled = false;
    }

    @Input() set gig(value: GigViewModel) {
        if (value == null) {
            value = new GigViewModel(null, false, this.dialogService, this.loginService);
        }
        this.gigModel = value;
        this.buttonsDisabled = this.gigModel.id === 0;
        this.updateGenres();
        this.updateArtists();
        this.updateCities();
    }

    get gig(): GigViewModel {
        return this.gigModel;
    }

    get editGenre(): GenreViewModel {
        return $EditGenre;
    }

    set editGenre(value: GenreViewModel) {
        $EditGenre = value;
    }

    get editCity(): CityViewModel {
        return $EditCity;
    }

    set editCity(value: CityViewModel) {
        $EditCity = value;
    }

    get editArtist(): ArtistViewModel {
        return $EditArtist;
    }

    set editArtist(value: ArtistViewModel) {
        $EditArtist = value;
    }

    ngOnInit() {
    }

    onDeleteGigClicked() {
        const NO_BUTTON = new DialogButton(() => { });
        NO_BUTTON.title = 'Nein';
        const YES_BUTTON = new DialogButton(() => {
            new DeleteGigCommand(this.gig.id)
                .catch(error => this.onError(error))
                .then(() => this.gigModel.onCancelClicked())
                .execute();
        });
        YES_BUTTON.title = 'Ja, Löschen';
        const buttons: DialogButton[] = [
            NO_BUTTON,
            YES_BUTTON
        ];
        this.dialogService.showDialog('Gig löschen', `Sicher, dass Sie den Gig {this.gig.id} löschen möchten?`, buttons, 600);
    }

    onCancelClicked() {
        this.gig.onCancelClicked();
    }

    onSaveClicked() {
        this.gig.onSavedClicked();
    }

    onDeleteSelectedGenreClicked() {
    }

    onPrepareNewGenreClicked() {
        handleDone(this.editGenre);
        this.editGenre = new GenreViewModel(0, '');
    }

    onSaveGenreClicked() {
        this.extendEditList(this.editGenre, this.genres)
            .then(() => this.onPrepareNewGenreClicked());
    }

    onCityDeleteButtonClicked() {
        // TODO
    }

    onCityEditButtonClicked() {
        const selCity = this.cities.find(c => c.editSelected);
        if (selCity == null) {
            throw Error('No city selection regognized!');
        }
        this.editCity = new CityViewModel(selCity.id, selCity.name);
    }

    onCitySaveAddButtonClicked() {
        // cmd ADD
        new SaveNewCityCommand(this.editCity)
            .catch(error => this.onError(error))
            .then((cityId: number) => {
                this.updateCities();
                this.editCity = new CityViewModel(0, '');
            })
            .execute();
    }

    onCitySaveNameButtonClicked() {
        const selectedCity: CityViewModel = this.editCity;
        if (selectedCity == null) {
            throw Error('No city set!');
        }

        // neuer Städtename
        if (selectedCity.id === 0) {
            new NewCityCommand(selectedCity.name)
                .catch(error => this.onErrorDone(selectedCity))
                .then(id => {
                    selectedCity.id = id;
                    handleDone(selectedCity);
                    this.updateCities();
                    this.onPrepareNewCityClicked();
                })
                .execute();
        } else {
            new EditCityCommand(selectedCity)
                .catch(error => this.onErrorDone(error, selectedCity))
                .then(() => {
                    handleDone(selectedCity);
                    this.updateCities();
                })
                .execute();
        }
    }

    onDeleteSelectedArtistClicked() {
        const selectedArtist: ArtistViewModel = this.editArtist;
        if (selectedArtist != null && selectedArtist.editSelected) {
            new DeleteArtistCommand(selectedArtist)
                .catch(error => this.onErrorDone(error, selectedArtist))
                .then(() => {
                    this.onDone(selectedArtist);
                    this.onPrepareNewArtistClicked();
                    this.updateArtists();
                })
                .execute();
        }
    }

    onPrepareNewArtistClicked() {
        handleDone(this.editArtist);
        this.editArtist = new ArtistViewModel(0, '');
    }

    onPrepareNewCityClicked() {
        handleDone(this.editCity);
        this.editCity = new CityViewModel(0, '');
    }

    onSaveArtistClicked() {
        const artistToSave: ArtistViewModel = this.editArtist;
        if (artistToSave == null) {
            throw Error('No artistToSave!');
        }

        // anlegen
        if (artistToSave.id <= 0) {
            artistToSave.disabled = true;

            new CreateArtistCommand(artistToSave.name)
                .catch(error => this.onError(error))
                .then(id => {
                    artistToSave.id = id;
                    this.updateArtists();
                    this.onPrepareNewArtistClicked();
                })
                .execute();
        } else {

        }

        // nur demo:
        // this.extendEditList(this.editArtist, this.artists)
        //     .catch(error => console.log(error))
        //     .then(() => this.onPrepareNewArtistClicked());
    }

    async extendEditList(item: IdObject, targetList: Array<IdObject>): Promise<{}> {
        return new Promise((resolve, reject) => {
            const value = item.name;
            const id = item.id;
            if (id === 0) {
                if (notNullOrEmpty(value)) {
                    if (!targetList.find(e => e.name.toLowerCase() === value.toLowerCase())) {
                        targetList.push(item);
                        resolve();
                    } else {
                        const error = 'Der Name existiert schon!';
                        this.bubbleService.showBubble(1000, error);
                        reject(error);
                    }
                }
            } else if (id > 0) {
                if (notNullOrEmpty(value)) {
                    const error = 'Der Name darf nicht leer sein!';
                    this.bubbleService.showBubble(1000, error);
                    reject(error);
                }
            }
        });
    }

    onGenreSelectionChanged(current: GenreViewModel) {
        handleSingleSelection(this.genres, current);
    }

    onCitySelectionChanged(current: CityViewModel) {
        handleSingleSelection(this.cities, current);
    }

    onArtistSelectionChanged(current: ArtistViewModel) {
        handleSingleSelection(this.artists, current);
    }

    private onError(error: any, delayMs: number = 1000) {
        console.log(error);
        this.bubbleService.showBubble(delayMs, `Fehler: {error}`);
    }

    private onErrorDone(error: any, model: IdObject = null, delayMs: number = 1000) {
        console.log(error);
        this.bubbleService.showBubble(delayMs, `Fehler: {error}`);
        this.onDone(model);
    }

    private onDone(model: IdObject = null) {
        if (model != null) {
            handleDone(model);
        }
    }

    private async updateGenres() {
        const mapToGenre = genre => new GenreViewModel(genre.id, genre.name);
        console.log('INFO - Genres werden abgerufen');

        new GetAllGenresCommand()
            .catch(error => this.bubbleService.showBubble(1000, error))
            .then((genres: MinifiedJSON[]) => {
                this.genres = new Array<GenreViewModel>();
                this.genres = this.genres.concat(genres.map(mapToGenre));
                console.log(`INFO - {this.genres.length} Genres abgerufen`);
            }).execute();
    }

    private async updateArtists() {
        const mapToArtist = artist => new ArtistViewModel(artist.id, artist.name);
        console.log('INFO - Künstler werden abgerufen');

        new GetAllArtistsCommand()
            .catch(error => this.bubbleService.showBubble(1000, error))
            .then((artists: MinifiedJSON[]) => {
                this.artists = new Array<ArtistViewModel>();
                this.artists = this.artists.concat(artists.map(mapToArtist));
                console.log(`INFO - {this.artists.length} Künstler abgerufen`);
            }).execute();
    }

    private async updateCities() {
        const mapToCity = city => new CityViewModel(city.id, city.name);
        console.log('INFO - Städte werden abgerufen');

        new GetAllCitiesCommand()
            .catch(error => this.bubbleService.showBubble(1000, error))
            .then((cities: MinifiedJSON[]) => {
                this.cities = new Array<CityViewModel>();
                this.cities = this.cities.concat(cities.map(mapToCity));
                console.log(`INFO - {this.cities.length} Städte abgerufen`);
            }).execute();
    }
}
