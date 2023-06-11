import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import Select from 'react-select';
import AppNavbar from './AppNavbar';
class StudentEdit extends Component {

    emptyItem = {
        tckn: '',
        name: '',
        phoneNumber: '',
        city: '',
        district:''
    };
    cities = [];
    cityOptions = [];
    districts = [];
    districtOptions = [];
    selectedCity = { label: "", value: 0 }
    selectedDistrict = { label: "", value: 0 }

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            cities:this.cities,
            cityOptions:this.cityOptions,
            districts:this.districts,
            districtOptions:this.districtOptions,
            selectedCity:this.selectedCity,
            selectedDistrict:this.selectedDistrict

           
        };
       
        this.handleChange = this.handleChange.bind(this);
        this.handleCityChange = this.handleCityChange.bind(this);
        this.handleDistrictChange = this.handleDistrictChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const student = await (await fetch(`/students/${this.props.match.params.id}`)).json();
            const cities = await (await fetch(`/cities/`)).json();
            const cityOptions = cities.map(function (city) {
                return { value: city?.id, label: city?.cityName };
              })

              const selectedCity = {value: student?.city?.id, label: student?.city?.cityName}


            
            const selectedDistrict =  { value: student?.district?.districtName, label: student?.district?.districtName };

            student.city = selectedCity.label;
            student.district = selectedDistrict.value;
              
              this.setState({item: student, cities:cities, cityOptions:cityOptions, selectedCity:selectedCity, selectedDistrict:selectedDistrict});
        }

        else if (this.props.match.params.id === 'new') {
            const cities = await (await fetch(`/cities/`)).json();
            const cityOptions = cities.map(function (city) {
                return { value: city?.id, label: city?.cityName };
              })
               
              
              this.setState({cities:cities, cityOptions:cityOptions 
            });
        }
        

    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    handleDistrictChange(event) {
        console.log(event);
        const districts = this.state.districtOptions;
        let found = districts.find(function (element) {
            return element?.value === event.value;
        });
        
        const name = 'district';
        let item = {...this.state.item};
        item[name] = found.value
          this.setState({item: item, selectedDistrict:{value: found?.value, label:found?.value}});
        
    }

    handleCityChange(event) {
        console.log(event);
        const cities = this.state.cities;
        let found = cities.find(function (element) {
            return element?.id === event.value;
        });
        
        const districtOptions = found?.districts.map(function (district) {
            return { value: district?.districtName, label: district?.districtName };
          })
        const name = 'city';
        let item = {...this.state.item};
        item[name] = found?.cityName
          this.setState({item: item, districtOptions: districtOptions, selectedCity:{value: found?.id, label:found?.cityName}});
        
    }

async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/students' + (item.id ? '/' + item.id : ''), {
        method: (item.id) ? 'PUT' : 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(item),
    });
    this.props.history.push('/students');
}

    render() {
        const {item} = this.state;

        const title = <h2>{item.id ? 'Edit Student' : 'Add Student'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="tckn">TCKN</Label>
                        <Input type="text" name="tckn" id="tckn" value={item.tckn || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} />
                    </FormGroup>

                    <FormGroup>
                        <Label for="phoneNumber">Phone</Label>
                        <Input type="text" name="phoneNumber" id="phoneNumber" value={item.phoneNumber || ''}
                               onChange={this.handleChange} />
                    </FormGroup>

                    <FormGroup>
                    <Label for="city">City</Label>
                    <Select name = "city"

        options={this.state.cityOptions} // set list of the data
        value={this.state.selectedCity}
        onChange={(e) => this.handleCityChange(e)}

      />

                        </FormGroup>

                        <FormGroup>
                    <Label for="district">District</Label>
                    <Select name = "district"

        options={this.state.districtOptions} // set list of the data
        value={this.state.selectedDistrict}
        onChange={(e) => this.handleDistrictChange(e)}

      />

                        </FormGroup>


                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/students">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(StudentEdit);